package rest_controller;

import model.RemoteKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/RemoteKey")
public class RemoteKeyController {

    @GetMapping(path = "/")
    public String getRemoteKey(){
        return RemoteKey.key;
    }

}
