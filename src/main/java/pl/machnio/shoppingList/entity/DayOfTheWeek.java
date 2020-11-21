package pl.machnio.shoppingList.entity;

import javax.persistence.*;

@Entity
@Table(name = "day_of_the_week")
public class DayOfTheWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
