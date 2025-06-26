package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();

            return profile;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Profile getUserId(int id) {

        String sql = "SELECT * FROM profiles WHERE user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                Profile profile = new Profile();
                profile.setUserId(resultSet.getInt("user_id"));
                profile.setFirstName(resultSet.getString("first_name"));
                profile.setLastName(resultSet.getString("last_name"));
                profile.setEmail(resultSet.getString("email"));
                profile.setPhone(resultSet.getString("phone"));

                return profile;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error retrieving profile", e);
        }

        return null;
    }


    @Override
    public void update(int id, Profile updatedProfile) {

        String sql = """
        UPDATE profiles
        SET first_name = ?, last_name = ?, email = ?, phone = ?
        WHERE user_id = ?
    """;

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedProfile.getFirstName());
            statement.setString(2, updatedProfile.getLastName());
            statement.setString(3, updatedProfile.getEmail());
            statement.setString(4, updatedProfile.getPhone());
            statement.setInt(5, id);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error updating profile", e);
        }
    }

}
