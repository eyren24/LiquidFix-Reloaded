<span style="color:blue">LiquidFix</span>
Advanced fix tools and armor


Features
» Confirm fix GUI
» Custom Config
» Aliases
» Cost command
» Vault Economy support!
+ PlaceholderAPI support

Commands
» /liquidfix reload # reload plugin
» /fix # fix command aliases (fixall, repair)

Permissions
» /liquidfix liquidfix.admin
» Economy bypass liquidfix.bypass
Config:

## prefix: null -> no prefix
prefix: "&7&l[&c&lLiquidFix&7&l] "

economy: true
## prince for do this command 1000$
fix-prince: 1000

## messages
wrong-command: "&4Please usage /fix"
no-item-repairable: "&cThere's not repairable items in yor inventory"
insufficient-found: "&cYou don't have enough money! &7Current balance &f-> &e%vault_eco_balance%"
fixed-items: "&aYou fix all items!"
pay-confirm-message: "&eYou pay %liquidfix_fix_prince%$"
permission-denied: "&cYou don't have permissions"

banned-material:
 - POTION
  - GOLDEN_APPLE

## Menu
menu:
  title: "&aConfirm Fix"
  size: 9 # Must multiply of 9 [18 27 36]

items:
  first:
    item: SKULL_ITEM
    owner: Eyren24
    name: "&aConfirm"
    position: 5
    lore:
     - "&4First"
      - "&6Second"
    commands:
     - "[CONFIRM]"
  seconds:
    item: WOOL
    durability: 1
    name: "&cCancel"
    position: 9
    lore:
     - "&4First"
      - "&6Second"
    commands:
     - "[CLOSE]"
