package com.example.ms_vehiculos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="vehiculos")//--> asi se llamara la tabla en la base de datos
@Data//--> nos ahorra escribir getters y setters a mano
@NoArgsConstructor//-->constructor vacio
@AllArgsConstructor//--> constructor completo
@Builder//--> permite crear vehiculos de forma limpia
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//-> hace el id automatico
    private Integer id;

    //5 atributos---

}
