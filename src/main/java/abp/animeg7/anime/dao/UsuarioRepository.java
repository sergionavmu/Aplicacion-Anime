package abp.animeg7.anime.dao;

import abp.animeg7.anime.model.Anime;
import abp.animeg7.anime.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class UsuarioRepository {

    private final String GET_USERS = "call anime.getAllUsers()";
    private final String GET_USER_BY_ID = "call anime.getUserById(?)";
    private final String GET_USER_BY_EMAIL = "call anime.getUserByEmail(?)";
    private final String INSERT_USER = "call anime.insertUser(?, ?, ?, ?)";
    private final String UPDATE_USER = "call anime.updateUserByEmail(?, ?, ?, ?)";
    private final String DELETE_USER = "call anime.deleteUserByEmail(?)";

    private final String LOGIN_USER = "call anime.loginUser(?, ?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_ID,
                    new Object[]{id},
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL,
                    new Object[]{email},
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<User> getUser() {
        return jdbcTemplate.query(GET_USERS, new BeanPropertyRowMapper<>(User.class));
    }

    public boolean loginUser(String email, String password) {
        int authenticated = jdbcTemplate.queryForObject(LOGIN_USER, new Object[]{email, password}, Integer.class);
        return authenticated == 1;
    }
    public void addUser (User user) {
        jdbcTemplate.update(INSERT_USER,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone()
        );
    }

    public void updateUser (String email, User user) {
        jdbcTemplate.update(UPDATE_USER,
                email,
                user.getName(),
                user.getPassword(),
                user.getPhone()
        );
    }

    public void deleteUser (String email) {
        jdbcTemplate.update(DELETE_USER, email);
    }

}
