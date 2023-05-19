package com.gestion.empleados.controller;

import com.gestion.empleados.model.Empleado;
import com.gestion.empleados.repository.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;

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
        Empleado emp = repository.save(empleado);
        return ResponseEntity.ok(emp);
    }


    // Editar un empleado
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> updateEmp(@PathVariable Long id,@RequestBody Empleado empleado){
        Empleado EmpExiste = repository.getReferenceById(id); // se fija si existe este id
        if(EmpExiste != null){ // si el id no es nulo
            empleado.setId(id);
            Empleado updatedEmpresult = repository.save(empleado);
            return new ResponseEntity<>(updatedEmpresult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
