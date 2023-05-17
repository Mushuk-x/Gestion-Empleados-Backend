package com.gestion.empleados.controller;

import com.gestion.empleados.model.Empleado;
import com.gestion.empleados.repository.EmpleadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class EmpleadoController {
    private EmpleadoRepository repository;

    public EmpleadoController(EmpleadoRepository repository) {
        this.repository = repository;
    }

    //Buscar todos los empleados
    @GetMapping("/empleados")
    public List<Empleado> ListaEmpleados (){
        return repository.findAll();
    }


    // Buscar empleado mediante ID
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> FindId(@PathVariable Long id){
        Optional<Empleado> empleadoOpt = repository.findById(id);
        if (empleadoOpt.isPresent()){
            return ResponseEntity.ok(empleadoOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un empleado
    @PostMapping("/empleados")
    public ResponseEntity<Empleado> CreateEmp(@RequestBody Empleado empleado){
        if (empleado.getId() != null){return ResponseEntity.badRequest().build();}
        Empleado emp = repository.save(empleado);
        return ResponseEntity.ok(emp);
    }

    // Editar un empleado
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> updateEmp(@PathVariable Long id,@RequestBody Empleado empleado){
        if(id == null){
           return ResponseEntity.badRequest().build();
        }
        if(repository.existsById(empleado.getId())){
            empleado.setNombre(empleado.getNombre());
            empleado.setApellido(empleado.getApellido());
            empleado.setMail(empleado.getMail());
            Empleado emp = repository.save(empleado);
            return ResponseEntity.ok(emp);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un empleado
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Empleado> DeleteEmp(@PathVariable Long id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }








}
