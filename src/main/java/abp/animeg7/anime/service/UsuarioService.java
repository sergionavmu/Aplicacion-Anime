package abp.animeg7.anime.service;

import abp.animeg7.anime.dao.HandlerSax;
import abp.animeg7.anime.dao.UsuarioRepository;
import abp.animeg7.anime.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity getAllUsers() {
        List<User> users = usuarioRepository.getUser();
        if(users.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(users);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"response\":\"Error no hay usuarios para mostrar\"}");
    }

    public ResponseEntity getUserByEmail( String email) {
        User user = usuarioRepository.findByEmail(email);
        if(user != null)

            return ResponseEntity.status(HttpStatus.OK).body(user);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"response\":\"No hay usuarios con ese correo\"}");
    }



    //LOGIN
    public ResponseEntity loginUser(String email, String password) {
        User user = usuarioRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Email no exists\"}");
        }
    }

    //REGISTER
    public ResponseEntity addUser (User user) {
        if(user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getPhone() == null || user.getPhone().isEmpty() ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"response\":\"Todos los campos son obligatorios\"}");
        }

        if(!User.EmailValidator.validate(user.getEmail())){
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Mail incorrecto\"}");
        }

        User existingUser = usuarioRepository.findByEmail(user.getEmail());

        if(existingUser == null){
            usuarioRepository.addUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Usuario registrado correctamente\"}");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Usuario ya registrado\"}");
        }
    }


    public ResponseEntity updateUser (String email, User user) {

        User existingUser = usuarioRepository.findByEmail(email);

        if (existingUser!= null) {
            usuarioRepository.updateUser(email, user);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    public ResponseEntity deleteUser(String email) {

        User existingUser = usuarioRepository.findByEmail(email);

        if (existingUser != null) {
            usuarioRepository.deleteUser(email);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    public void dataLoad() {
        try {
            File file = new File("Files/Anime.xml");
            List<User> users = parseAnimeXML(file);
            for (User user : users) {
                usuarioRepository.addUser(user);
                System.out.println(user);
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static List<User> parseAnimeXML(File file) throws ParserConfigurationException, SAXException, IOException {
        HandlerSax handler = new HandlerSax();
        return handler.getUsers(file.getPath());
    }

}
