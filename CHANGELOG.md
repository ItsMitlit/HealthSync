
# HealthSync - Changelog
All notable changes to this project will be documented in this file.

## [1.0.3] - 2025-02-27

List of changes for the 1.0.3 release.

### Fixed
- **Immortality Bug**: Fixed a bug that made players immortal. (really sorry abt that lol)
- **Lava Bug**: Fixed a bug where lava damage would decrease health at mach 10 speed.

### Changed
- **Relative Rename**: Changed the "relative_damage" value in the config to "split-damage" for better readability.

## [1.0.2] - 2025-02-26

List of changes for the 1.0.2 release.

### Added
- **Relative Damage**: Added a new feature to calculate relative damage.
  - **Relative Damage Rounding**: Rounds relative damage to the nearest whole number.
- **Damage Effect**: Added a new feature to display the usual damage effect.
- **Changelog**: Added a changelog file to keep track of changes.
- **bStats**: Added bStats to track plugin usage statistics.

### Fixed
- **Health Surplus**: Fixed a bug where the plugin would attempt to increase player health above **20 points** and cause an error output in console. 

## [1.0.1] - 2025-02-01

List of changes for the 1.0.1 release.

### Added
- **Additional Comments**: Added additional comments for a better explanation.
- **Death Shaming**: Added the guilty player to the death messages of other players

### Changed

- **Better Death Announcements**: Replaced the non-specific (generic) death announcement with the player's actual death message.

### Fixed

- **Negative Health**: Fixed errors involving health values going into negatives due to bad death handling in the EntityDamageEvent.
- **Death Message Order**: Fixed problems to do with death message order.

## [1.0.0] - 2025-01-26

List of changes for the 1.0.0 release.

### Added
- **Initial Release**: Added the initial plugin release.

*based on [juampynr's changelog example](https://gist.github.com/juampynr/4c18214a8eb554084e21d6e288a18a2c)*