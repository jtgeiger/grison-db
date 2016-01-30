package com.sibilantsolutions.grison.db.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcChangelogDao implements ChangelogDao
{

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource( DataSource dataSource )
    {
        this.jdbcTemplate = new NamedParameterJdbcTemplate( dataSource );
    }

    @Override
    public long getSchemaVersion()
    {
        String sql = "SELECT MAX(ID) FROM CHANGELOG";

        Long version = jdbcTemplate.queryForObject( sql, (SqlParameterSource)null, Long.class );

        return version;
    }

}
