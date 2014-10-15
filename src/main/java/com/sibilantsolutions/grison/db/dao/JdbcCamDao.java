package com.sibilantsolutions.grison.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;
import com.sibilantsolutions.grison.driver.foscam.domain.Version;

@Repository
public class JdbcCamDao implements CamDao
{

    //final static private Logger log = LoggerFactory.getLogger( JdbcCamDao.class );

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource( DataSource dataSource )
    {
        this.jdbcTemplate = new JdbcTemplate( dataSource );
    }

    @Override
    public void alarm( AlarmTypeE alarmType, Timestamp timestamp, Number sessionDbID )
    {
        String sql = "INSERT INTO cam_alarm (alarm_type, alarm_time, cam_session) VALUES (:alarmType, :timestamp, :sessionDbID)";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue( "alarmType", alarmType.getValue() )
            .addValue( "timestamp", timestamp )
            .addValue( "sessionDbID", sessionDbID );

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate( jdbcTemplate );
        namedParameterJdbcTemplate.update( sql, namedParameters );
    }

    @Override
    public Number sessionConnected( final String cameraId, final Version firmwareVersion, final Timestamp timestamp )
    {
//        final String sql = "INSERT INTO cam_session (camera_id, firmware_version_major, firmware_version_minor, firmware_version_patch, firmware_version_build, connect_time) VALUES (:cameraId, :firmwareVersionMajor, :firmwareVersionMinor, :firmwareVersionPatch, :firmwareVersionBuild, :timestamp)";
//
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//            .addValue( "cameraId", cameraId )
//            .addValue( "firmwareVersionMajor", firmwareVersion.getMajor() )
//            .addValue( "firmwareVersionMinor", firmwareVersion.getMinor() )
//            .addValue( "firmwareVersionPatch", firmwareVersion.getPatch() )
//            .addValue( "firmwareVersionBuild", firmwareVersion.getBuild() )
//            .addValue( "timestamp", timestamp );
//
//        namedParameterJdbcTemplate.update( sql, namedParameters );

        final String sql = "INSERT INTO cam_session (camera_id, firmware_version_major, firmware_version_minor, firmware_version_patch, firmware_version_build, connect_time) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                        int i = 1;
                        ps.setString(i++, cameraId);
                        ps.setInt(i++, firmwareVersion.getMajor());
                        ps.setInt(i++, firmwareVersion.getMinor());
                        ps.setInt(i++, firmwareVersion.getPatch());
                        ps.setInt(i++, firmwareVersion.getBuild());
                        ps.setTimestamp(i++, timestamp);
                        return ps;
                    }
                },
                keyHolder);

        //log.info( "Inserted record id={}.", keyHolder.getKey() );
        return keyHolder.getKey();
    }

}
