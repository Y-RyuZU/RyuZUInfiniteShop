/*
 * This file was generated by the Gradle "init" task.
 */

rootProject.name = "SearchableInfiniteShop"
include(":searchableinfiniteshop-api")
include(":searchableinfiniteshop-v16older")
include(":searchableinfiniteshop-v16newer")
include(":searchableinfiniteshop-plugin")
project(":searchableinfiniteshop-plugin").projectDir = file("plugin")
project(":searchableinfiniteshop-api").projectDir = file("api")
project(":searchableinfiniteshop-v16older").projectDir = file("v16older")
project(":searchableinfiniteshop-v16newer").projectDir = file("v16newer")