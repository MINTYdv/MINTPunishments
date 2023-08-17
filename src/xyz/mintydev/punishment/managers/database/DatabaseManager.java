package xyz.mintydev.punishment.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.zaxxer.hikari.HikariDataSource;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;

public class DatabaseManager {

	private final MINTPunishment main;
	private static DatabaseManager instance;

	private RowSetFactory rowSetFactory;

	private HikariDataSource dataSource;

	public DatabaseManager(MINTPunishment main) {
		this.main = main;
		instance = this;

		setup();
	}

	/**
	 * Function called to setup the database/hikari database connection
	 */
	private void setup() {
		try {
			dataSource = new DBDataSource().getNewDataSource();
		} catch (Exception e) {
			main.getLogger().log(Level.SEVERE,
					"Could not establish a connection to the MySQL Database. Stopping the plugin.");
			main.getPluginLoader().disablePlugin(main);
			return;
		}

		if (!isConnectionValid()) {
			main.getLogger().log(Level.SEVERE,
					"Could not establish a connection to the MySQL Database. Stopping the plugin.");
			main.getPluginLoader().disablePlugin(main);
			return;
		}

		executeStatement(SQLQuery.CREATE_TABLE_ACTIVE);
		executeStatement(SQLQuery.CREATE_TABLE_EXPIRED);
		executeStatement(SQLQuery.DELETE_OLD_PUNISHMENTS, new Date().getTime());
	}

	/**
	 * Shutdown the database connection
	 */
	public void shutdown() {
		if (dataSource == null)
			return;
		dataSource.close();
	}

	/**
	 * Execute a sql statement without having to obtain any results.
	 *
	 * @param sql        the sql statement
	 * @param parameters the parameters
	 */
	public void executeStatement(SQLQuery query, Object... parameters) {
		executeStatement(query, false, parameters);
	}

	/**
	 * Function to execute a sql statement.
	 *
	 * @param sql        the sql query
	 * @param parameters the parameters
	 * @return the result set
	 */
	public ResultSet executeStatement(SQLQuery query, boolean result, Object... parameters) {
		return executeStatement(query.toString(), result, parameters);
	}

	public ResultSet executeResultStatement(SQLQuery query, Object... parameters) {
		return executeStatement(query, true, parameters);
	}

	public void insertPunishment() {

	}

	private synchronized ResultSet executeStatement(String sql, boolean result, Object... parameters) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			for (int i = 0; i < parameters.length; i++) {
				statement.setObject(i + 1, parameters[i]);
			}
			if (result) {
				CachedRowSet results = createCachedRowSet();
				results.populate(statement.executeQuery());
				return results;
			}
			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Get a Punishment from a {@link ResultSet}
	 *
	 * @param rs the result set
	 * @return the punishment from the result set
	 * @throws SQLException the sql exception
	 */
	public Punishment getPunishmentFromResultSet(ResultSet rs) throws SQLException {
		Punishment res = new Punishment(PunishmentType.valueOf(rs.getString("type")),
				UUID.fromString(rs.getString("uuid")), rs.getString("name"),
				rs.getString("operator") != null ? UUID.fromString(rs.getString("operator")) : null,
				rs.getString("operatorName"), new Date(rs.getLong("start")), new Date(rs.getLong("end")),
				rs.getString("reason"));

		if (rs.getInt("id") != 0) {
			res.setId(rs.getInt("id"));
		}

		return res;
	}

	private CachedRowSet createCachedRowSet() throws SQLException {
		if (rowSetFactory == null) {
			rowSetFactory = RowSetProvider.newFactory();
		}
		return rowSetFactory.createCachedRowSet();
	}

	public void initPool() {
		setup();
	}

	public void closePool() {
		this.dataSource.close();
	}

	public Connection getConnection() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			main.getLogger().warning("ERROR : Could not connect to database.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Check whether there is a valid connection to the database.
	 *
	 * @return whether there is a valid connection
	 */
	public boolean isConnectionValid() {
		if (dataSource == null)
			return false;
		return dataSource.isRunning();
	}

	/*
	 * Getters & Setters
	 */

	public static DatabaseManager get() {
		return instance;
	}

}
