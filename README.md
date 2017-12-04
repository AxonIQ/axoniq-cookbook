# axon-cookbook
Several axon recipes as example of how to tackle specific problems

## Code
Under code are the small projects that are used for creating the recipes. Not all recipes might use the same code base (for example use of Spring Boot or not in a recipe). So preferably add to the directory for which recipe the project is used.

## Recipes
In `/recipes` are all recipes for the cookbook. 

##### Template for recipes
Under `/recipes` is a file called `0_asciidoc_axon_cookbook_template.adoc` which contains the template for each of the recipes. 

If you change something in the template, make sure each of the recipes are still working accordingly. 

##### Naming
Please name the recipe including the number of the issue. So, for example ```11_basic_axon_application_with_spring_boot.adoc```
 
Issues can be found here: https://github.com/AxonIQ/axon-cookbook/projects/1. 

##### Versioning
Make sure in each of the recipes on which version of Axon Framework (and probably other dependencies) the recipe is based. 

If a new recipe needs to be created for a newer version of the framework, please create a folder containing the new version number, like `version_3.0.6` and add the new recipe in this folder. The new recipe can be a "copy paste" from the old recipe but make sure all required steps are adjusted for the new version. 

##### Review
Make sure on of the team members or colleagues walks through the recipe to check whether the steps are clear and if the code compiles and runs at the end of the recipe

