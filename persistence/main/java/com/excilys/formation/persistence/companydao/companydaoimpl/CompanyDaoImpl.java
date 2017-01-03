package com.excilys.formation.persistence.companydao.companydaoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.CustomResultSetExtractor;
import com.excilys.formation.mapper.CustomRowMapper;
import com.excilys.formation.mapper.PersistenceMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.companydao.CompanyDao;

/**
 * DAO class for companies.
 * 
 * @author Euronia
 *
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final CompanyDaoImpl COMPANY_DAO_INSTANCE;
    private static Logger logger;
    private static final String SELECT_BY_NAME = "SELECT * FROM company WHERE id=?";
    private static final String SELECT_PAGE = "SELECT * FROM company LIMIT ? OFFSET ?";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM company";
    private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE computer.company_id = ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE company.id = ?";
    private static final ResultSetExtractor<Company> EXTRACTOR_COMPANY = CustomResultSetExtractor.singletonExtractor(CustomRowMapper.MAPPER_COMPANY);
    
    
    static {
        logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cdbLogger");
        COMPANY_DAO_INSTANCE = new CompanyDaoImpl();
    }

    ////////// Getters & Setters //////////

    /**
     * Getter for the instance of CompanyDaoJdbc.
     * 
     * @return the instance of CompanyDaoJdbc
     */
    public static CompanyDaoImpl getInstance() {
        return COMPANY_DAO_INSTANCE;
    }

    public void setDataSource(DataSource data) {
        dataSource = data;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ////////// Methods //////////

    @Override
    public Company getById(int pId) throws PersistenceException {
        return jdbcTemplate.query(SELECT_BY_NAME, EXTRACTOR_COMPANY, pId);
    }

    @Override
    public Page<Company> getPage(Page<Company> page) throws PersistenceException {
        List<Company> allCompanies = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAGE);) {
            preparedStatement.setLong(1, page.getElementsByPage());
            preparedStatement.setLong(2, (page.getCurrentPage() - 1) * page.getElementsByPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            allCompanies = PersistenceMapper.mapResultsToCompanyList(resultSet);
            page.setElements(allCompanies);
            page.setTotalElements(count());
            resultSet.close();
        } catch (SQLException e) {
            logger.error("CompanyDAO : GetPage() catched SQLException and throwed PersistenceException");
            throw new PersistenceException("Problème lors de la récupération de la page de compagnies", e);
        }
        return page;
    }

    /**
     * Count the total number of companies.
     * 
     * @return the number of companies in the DB
     */

    private int count() {
        int total = 0;
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            if (resultSet.next()) {
                total = resultSet.getInt("total");
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("CompanyDAO : count() catched SQLException ");
            logger.error(e.getStackTrace().toString());
        }
        return total;
    }

    public void delete(long id, Connection connection) throws PersistenceException {
        try (PreparedStatement preparedStatementDeleteCompany = connection.prepareStatement(DELETE_COMPANY);) {

            preparedStatementDeleteCompany.setLong(1, id);
            preparedStatementDeleteCompany.executeUpdate();
        } catch (SQLException e) {
            logger.error("CompanyDAO : delete(long) catched SQLException and rollbacked");
            throw new PersistenceException(e);
        }

    }
}