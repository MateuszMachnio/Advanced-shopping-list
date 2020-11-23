package pl.machnio.shoppingList.entity;

import javax.persistence.*;

@Entity
@Table(name = "plan_schedule")
public class PlanSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
