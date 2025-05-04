rootProject.name = "WorldEditCUI"

pluginManagement {
    repositories {
        // mirrors:
        // - https://maven.architectury.dev/
        // - https://maven.fabricmc.net/
        maven(url = "https://maven.enginehub.org/repo/") {
            name = "enginehub"
        }
        // maven("https://maven.fabricmc.net/")
         gradlePluginPortal()
    }
}

sequenceOf(
    "fabric",
).forEach {
    include("worldeditcui-$it")
}
