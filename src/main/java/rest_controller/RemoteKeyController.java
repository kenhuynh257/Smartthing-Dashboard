package rest_controller;

import model.RemoteKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/RemoteKey")
public class RemoteKeyController {

    @GetMapping(path = "/")
    public String getRemoteKey() {
        return RemoteKey.key;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public String setRemoteKey(@RequestBody String key) {
        RemoteKey.key = key;
        return RemoteKey.key;
    }
}