package org.mascheraveneziana.zitan.web.v1;

import org.mascheraveneziana.zitan.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    @Autowired
    MeetingRepository mRepository;
  
    @RequestMapping(method = RequestMethod.GET)
    public String getMeeting() {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
          json = mapper.writeValueAsString(mRepository.findAll());
          return json;
        } catch (JsonProcessingException e) {
          e.printStackTrace();
          return "{\"error\": \"Can not read Meeting Data.\"}";
        }
    }

    
    @RequestMapping(method = RequestMethod.POST)
    public String createMeeting() {
        String str = "{ \"name\": \"Zitan\", \"version\": \"1.0.0\" }";
        return str;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateMeeting() {
        String str = "{ \"name\": \"Zitan\", \"version\": \"1.0.0\" }";
        return str;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteMeeting() {
        String str = "{ \"name\": \"Zitan\", \"version\": \"1.0.0\" }";
        return str;
    }

}
