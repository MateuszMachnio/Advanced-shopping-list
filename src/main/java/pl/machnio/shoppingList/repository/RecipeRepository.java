package pl.machnio.shoppingList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.machnio.shoppingList.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
