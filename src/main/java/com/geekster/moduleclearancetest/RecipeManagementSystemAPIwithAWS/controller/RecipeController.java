package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.controller;


import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.enums.RecipeType;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Recipe;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.AuthenticationService;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.RecipeService;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;



    @GetMapping("/{recipeId}")
    public Recipe getRecipe(@PathVariable Long recipeId) {
        return recipeService.findRecipe(recipeId);
    }

    @GetMapping
    public Iterable<Recipe>getAllRecipes()
    {
        return recipeService.getRecipes();
    }

    @PostMapping("/authenticateToken")
    private String authenticateToken(String authToken) {
        // Use the AuthenticationService to validate the token
        return authenticationService.authenticate(authToken);
    }

    @PostMapping("/recipes")
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe, @RequestHeader("Authorization") String authToken) {
        // Validate the authentication token
        String email = authenticateToken(authToken);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        // Create the recipe
        return ResponseEntity.ok(userService.createRecipe(recipe, email));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> removeRecipe(@PathVariable Long recipeId, @RequestHeader("Authorization") String authToken) {
        // Validate the authentication token
        String email = authenticateToken(authToken);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        // Delete the recipe
        return ResponseEntity.ok(userService.removeRecipe(recipeId, email));
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<String> updateRecipe(
            @PathVariable Long recipeId,
            @RequestBody Recipe updatedRecipe,
            @RequestParam RecipeType updatedRecipeType, // Include the RecipeType parameter
            @RequestHeader("Authorization") String authToken
    ) {
        // Validate the authentication token
        String email = authenticateToken(authToken);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        // Update the recipe
        return ResponseEntity.ok(recipeService.updateRecipe(recipeId, updatedRecipe, updatedRecipeType, email));
    }
}
