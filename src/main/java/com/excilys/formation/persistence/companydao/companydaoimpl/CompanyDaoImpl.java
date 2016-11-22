package com.excilys.formation.persistence.companydao.companydaoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.PersistenceMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.companydao.CompanyDao;
import com.excilys.formation.persistence.connectionprovider.ConnectionProvider;
import com.excilys.formation.persistence.connectionprovider.JdbcConnectionProvider;

/**
 * DAO class for companies.
 * @author Euronia
 *
 */
public class CompanyDaoImpl implements CompanyDao {
    private ConnectionProvider connectionProvider;
    private static CompanyDaoImpl companyDaoImpl = null;
    private static final String SELECT_BY_NAME = "SELECT * FROM company WHERE id=?";
    private static final String SELECT_PAGE = "SELECT * FROM company LIMIT ? OFFSET ?";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM company";
    /**
     * CompanyDaoJdbc constructor.
     * Initialize the connectionProvider.
     */
    private CompanyDaoImpl() {
        connectionProvider =  JdbcConnectionProvider.getInstance();
    }
    /**
     * Getter for the instance of CompanyDaoJdbc.
     * If the instance is null, initializes it.
     * @return the instance of CompanyDaoJdbc
     */
    public static CompanyDaoImpl getInstance() {
        if (companyDaoImpl == null) {
            companyDaoImpl = new CompanyDaoImpl();
        }
        return companyDaoImpl;
    }
    @Override
    public Company getById(int pId) throws PersistenceException {
        connectionProvider.openConnection();
        Company company = null;
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(SELECT_BY_NAME);
            preparedStatement.setInt(1, pId);
            ResultSet resultSet = preparedStatement.executeQuery();
            company = PersistenceMapper.mapResultToCompany(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException("Problème lors de la récupération de la compagnie");
        }
        connectionProvider.closeConnection();
        return company;
    }
    @Override
    public Page<Company> getPage(Page<Company> pPage) throws PersistenceException {
        connectionProvider.openConnection();
        List<Company> allCompanies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(SELECT_PAGE);
            preparedStatement.setInt(1, pPage.getElementsByPage());
            preparedStatement.setInt(2, (pPage.getCurrentPage() - 1) * pPage.getElementsByPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            allCompanies = PersistenceMapper.mapResultsToCompanyList(resultSet);
            pPage.setElements(allCompanies);
            pPage.setTotalElements(count());
        } catch (SQLException e) {
            throw new PersistenceException("Problème lors de la récupération de la page de compagnies");
        }
        connectionProvider.closeConnection();
        return pPage;
    }
    /**
     * Count the total number of companies.
     * @return the number of companies in the DB
     */
    private int count() {
        int total = 0;
        try {
            Statement statement = connectionProvider.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            if (resultSet.next()) {
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}