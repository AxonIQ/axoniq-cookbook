# Axon Cookbook
Several axon recipes as example of how to tackle specific problems

## Code
Under code are the small projects that are used for creating the recipes. Not all recipes might use the same code base (for example use of Spring Boot or no Spring Boot in a recipe). So preferably add to the directory (name) for which recipe the project is used.

Want to adjust one of the recipes or add a new one. Check out the master branch and create your own branch based on it. Add a pull request and request a review. If changes / additions are approved, the change will be merged to master. 

## Recipes
In the version specific folders are all recipes for the cookbook. 

##### Template for recipes
Under `/recipes` is a file called `0_asciidoc_axon_cookbook_template.adoc` which contains the template for each of the recipes. 

If you change something in the template, make sure each of the recipes are still working accordingly. 

##### Naming
Please name the recipe including the number of the issue. So, for example ```11_basic_axon_application_with_spring_boot.adoc```
 
Issues can be found here: https://github.com/AxonIQ/axoniq-cookbook/projects/1. 

##### Versioning
Make sure in each of the recipes on which version of Axon Framework (and probably other dependencies) the recipe is based. 

If a new recipe needs to be created for a newer version of the framework, please create a folder containing the new version number, like `version-3.1.X` and add the new recipe in this folder. The new recipe can be a "copy paste" from the old recipe but make sure all required steps are adjusted for the new version. 

