package org.example.logitrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "agents")
public class Agent extends User {
}
