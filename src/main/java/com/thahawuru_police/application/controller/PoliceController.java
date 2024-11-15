package com.thahawuru_police.application.controller;

import lombok.extern.slf4j.Slf4j;
import com.thahawuru_police.application.dto.response.ApiResponse;
import com.thahawuru_police.application.dto.response.PoliceResponseDTO;
import com.thahawuru_police.application.entity.PoliceOfficer;
import com.thahawuru_police.application.service.PoliceOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/police")
@Slf4j
public class PoliceController {

    @Autowired
    private PoliceOfficerService policeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PoliceResponseDTO>>> getOfficers(){
        List<PoliceResponseDTO> officers =policeService.allOfficers();
        ApiResponse<List<PoliceResponseDTO>> response = new ApiResponse<>(HttpStatus.OK.value(),officers,"suceess");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{userid}")
    public ResponseEntity<ApiResponse<PoliceResponseDTO>> getOfficer(@PathVariable UUID userid){
        PoliceResponseDTO officer  = policeService.getOfficer(userid);
        ApiResponse<PoliceResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(),officer,"success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/edit/{userid}")
    public ResponseEntity<ApiResponse<PoliceResponseDTO>> updateOfficer(@RequestBody PoliceOfficer police, @PathVariable(name = "userid") String id){
        police.setPoliceId(id);
        PoliceResponseDTO officer = policeService.updateOfficer(police);
        ApiResponse<PoliceResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(),officer,"success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //change user to inactive mode
    @DeleteMapping("/delete/{userid}")
    public ResponseEntity<ApiResponse<PoliceResponseDTO>> deleteOfficer(@PathVariable("userid") UUID id){
        PoliceResponseDTO officer = policeService.deleteOfficer(id);
        ApiResponse<PoliceResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(),officer,"success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
