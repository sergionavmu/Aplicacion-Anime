package abp.animeg7.anime.api;

import abp.animeg7.anime.model.User;
import abp.animeg7.anime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    public UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity getUsers() {
        return usuarioService.getAllUsers();
    }

    @GetMapping("/{email}")
    public ResponseEntity getUsersByEmail(@PathVariable String email) {
        return usuarioService.getUserByEmail(email);
    }
    @PostMapping("/dataloads")
    public ResponseEntity dataLoad() {
        usuarioService.dataLoad();
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity loginUser( @RequestBody User user) {
        return usuarioService.loginUser(user.getEmail(), user.getPassword());
    }
    @PostMapping("/register")
    public ResponseEntity addUser(@RequestBody User user) {
        return usuarioService.addUser(user);
    }
    @PutMapping("/{email}")
    public ResponseEntity updateUser(@PathVariable String email, @RequestBody User user) {
        return usuarioService.updateUser(email, user);
    }
    @DeleteMapping("/{email}")
    public ResponseEntity deleteUser(@PathVariable String email) {
        return usuarioService.deleteUser(email);
    }

}
