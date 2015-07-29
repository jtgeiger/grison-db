package com.sibilantsolutions.grison.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;

@Repository
public class JdbcCamImageDao implements CamImageDao
{

    //final static private Logger log = LoggerFactory.getLogger( JdbcCamDao.class );

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource( DataSource dataSource )
    {
        this.jdbcTemplate = new JdbcTemplate( dataSource );
    }

    @Override
    public Number insertImage(final VideoDataText vdt, long now, final String filename, final Number sessionDbID)
    {

        final String sql = "INSERT INTO cam_image (image_name, cam_timestamp, server_timestamp, uptime, cam_session) VALUES (?, ?, ?, ?, ?)";

        final Timestamp nowTs = new Timestamp( now );
        final Timestamp camTs = new Timestamp(vdt.getTimestampMs());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                        int i = 1;
                        ps.setString(i++, filename);
                        ps.setTimestamp(i++, camTs);
                        ps.setTimestamp(i++, nowTs);
                        ps.setLong(i++, vdt.getUptimeMs());
                        ps.setLong(i++, (Long) sessionDbID);
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey();
    }

}
