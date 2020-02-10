package de.schornyy.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private File file;
    private FileConfiguration cfg;

    public String prefix
            , PlayerhasnoPermissions, Player_isnt_Online, Player_deleted_Player_Rasse, Player_dosnt_have_Rasse, Player_set_Player_Rasse, Player_create_NPC, Player_deleted_NPC, Player_setSpawn_NPC
            , Player_setRasse_NPC, Player_setEntityType_NPC, Player_setName_NPC, Player_setRasse, Player_has_Rasse, Player_joined_Rasse, Player_hasnt_Invite, Player_denyed_Invite
            , Player_has_default_Job, Player_set_Player_default_job, Player_set_Player_Job, Player_cant_use_Block, Player_joined_job, Player_has_job, Player_cant_craft_item
            , Player_has_wrong_Job, Player_collect_Item, Player_setMidItem, Player_setEndItem
            , Rasse_exists, Rasse_created, Rasse_dosnt_exists, Rasse_deleted, Rasse_removed_Effect
            , PotionEffect_dosnt_exists, Potion_effect_added
            , NPC_dosnt_exists, NPC_hasnt_Rasse
            , Entity_dosnt_exists
            , NeedItemInHand
            , Job_dosnt_exists, Job_already_exists, Job_created, Job_deleted, Job_already_containsitem, Job_additemtoBlacklist, Job_NedditemtoaddtoBlacklist
            , Job_dosnt_containsitem, Job_removed_item_from_Blacklist
            , Material_dosnt_exists
            , MustEnterNummber
            , Brue_in_use, Brue_start, Brue_cant_start, Brue_finished
            , Smith_dosnt_exists, Smith_deleted, Smith_addIngrediends, Smith_removeIngrediends, Smith_dosnt_contains_Item, Smith_setAcessableJob, Smith_setBrueTime, Smith_Alreadyexists, Smith_created;

    public Config() {
        file = new File("plugins/Troniacraft/Config.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        loadDefault();
        loadinits();
    }

    public void loadDefault() {
        if(!getFile().exists()) {
            getCfg().set("Prefix", "&bTroniacraft &f>> ");
            getCfg().set("Player_setMidItem", "&aDu hast das Mid Item gesetzt!");
            getCfg().set("Player_setEndItem", "&aDu hast das End Item gesetzt!");
            getCfg().set("PlayerhasnoPermissions", "&cDu hast keine Rechte darauf!");
            getCfg().set("Rasse_exists", "&cDie Rasse existiert bereits!");
            getCfg().set("Rasse_created", "&aDu hast erfolgreich die Rasse erstellt!");
            getCfg().set("Player_isnt_Online", "&cDer Spieler ist nicht Online!");
            getCfg().set("Player_deleted_Player_Rasse", "&aDu hast die Rasse von &e%player% &aerfolgreich gewlöscht!");
            getCfg().set("Player_dosnt_have_Rasse", "&cDer Spieler ist in keiner Rasse");
            getCfg().set("Rasse_dosnt_exists", "&cDie Rasse existiert nicht!");
            getCfg().set("Rasse_deleted", "&aDu hast die Rasse erfolgreich gelöscht!");
            getCfg().set("Player_set_Player_Rasse", "&aDu hast die Rasse von %player% erfolgreich geändert!");
            getCfg().set("Rasse_removed_Effect", "&aDu hast den PotionEffect entfernt");
            getCfg().set("PotionEffect_dosnt_exists", "&cDer Potion Effect existiert nicht!");
            getCfg().set("Potion_effect_added", "&aDu hast erfolgreich den PotionEffect hintugefügt");
            getCfg().set("NPC_dosnt_exists", "&cDer NPC existiert nicht!");
            getCfg().set("Player_create_NPC", "&aDu hast einen NPC erstellt!");
            getCfg().set("Player_deleted_NPC", "&cDu hast ein NPC gelöscht!");
            getCfg().set("Player_setSpawn_NPC", "&aDu hast den Spawn für ein NPC gesetzt!");
            getCfg().set("Player_setRasse_NPC", "&aDu hast die Rasse erfolgreich geändert!");
            getCfg().set("Player_setEntityType_NPC", "&aDu hast das EntityType erfolgreich geändert!");
            getCfg().set("Entity_dosnt_exists", "&aDieses EntityTYpe existiert nicht!");
            getCfg().set("Player_setName_NPC", "&aDu hast den Namen erfolgreich geändert!");
            getCfg().set("NPC_hasnt_Rasse", "&cDer NPC gehört keiner Rasse an!");
            getCfg().set("Player_setRasse", "&aDu bist einer Rasse beigetreten!");
            getCfg().set("Player_has_Rasse", "&cDu hast bereits eine Rasse");
            getCfg().set("Player_joined_Rasse", "&aDu bist der Rasse beigetreten!");
            getCfg().set("Player_hasnt_Invite", "&cDu hast keine Rassen Einladung!");
            getCfg().set("Player_denyed_Invite", "&aDu hast die Rassen Einladung ablegelent!");
            getCfg().set("Job_dosnt_exists", "&cDer Job existiert nicht!");
            getCfg().set("Job_already_exists", "&cDer Job existiert bereits");
            getCfg().set("Job_created", "&aDu hast den Job erfolgreich erstellt");
            getCfg().set("Job_deleted", "&cDu hast den Job gelöscht!");
            getCfg().set("Player_has_default_Job", "&cDer Spieler hat bereits den Default Job!");
            getCfg().set("Player_set_Player_default_job", "&aDu hast den Job des Spielers auf default gesetzt!");
            getCfg().set("Player_set_Player_Job", "&aDu hast den Job von &e%player% &ageändert!");
            getCfg().set("Job_already_containsitem", "&cDas item ist bereits in der Blacklist");
            getCfg().set("Job_additemtoBlacklist", "&aDu hast das item der Blacklist hinzugefüght");
            getCfg().set("Job_NedditemtoaddtoBlacklist", "&cDu musst ein Item in der Hand halten!");
            getCfg().set("Material_dosnt_exists", "&cDas angegebene Material existiert nicht!");
            getCfg().set("Job_dosnt_containsitem", "&cDas Item existiert nicht in der Blacklist!");
            getCfg().set("Job_removed_item_from_Blacklist", "&aDu hast erfolgreich das item aus der Blacklist entfernt!");
            getCfg().set("Player_cant_use_Block", "&cDu darfst diesen Block nicht benutzen!");
            getCfg().set("Player_joined_job", "&aDu bist dem Job beigetreten!");
            getCfg().set("Player_has_job", "&cDu hast bereits einen Job");
            getCfg().set("Player_cant_craft_item", "&cDu darfst das Item nicht craften!");
            getCfg().set("Brue_in_use", "&cDer Kessel ist bereits in benutzung!");
            getCfg().set("Brue_start", "&aDu hast angefangen etwas zu brauen!");
            getCfg().set("Brue_cant_start", "&cDu kannst das nicht Brauen!");
            getCfg().set("Player_has_wrong_Job", "&cDu hast den falschen Job um das Rezept zu benutzen!");
            getCfg().set("Brue_finished", "&aDein Item ist fertig geschmolzen!");
            getCfg().set("Player_collect_Item", "&aDu hast dein Item erhalten!");
            getCfg().set("Smith_dosnt_exists", "&cDer Schmied existiert nicht!");
            getCfg().set("Smith_deleted", "&cDu hast den Schmied gelöscht!");
            getCfg().set("NeedItemInHand", "&cDu musst ein Item in der Hand halten!");
            getCfg().set("Smith_addIngrediends", "&aDu hast ein Ingrediend hinzugefüght!");
            getCfg().set("Smith_removeIngrediends", "&cDu hast ein Ingrediend entfernt!");
            getCfg().set("Smith_dosnt_contains_Item", "&cDas Ingrediend existiert nicht in der Liste!");
            getCfg().set("Smith_setAcessableJob", "&aDu hast den benutzbaren Job auf &e%job% &agesetzt");
            getCfg().set("MustEnterNummber", "&cDu musst eine Zahl angeben!");
            getCfg().set("Smith_setBrueTime", "&aDu hast die Schmied zeit auf &e%seconds% &agesetzt!");
            getCfg().set("Smith_Alreadyexists", "&cDas Schmiedrezept existiert breits!");
            getCfg().set("Smith_created", "&aDu hast das Schmiederezept erstellt!");

            try{
                getCfg().save(getFile());
            }catch (IOException e){}
        }
    }

    public void loadinits() {
        prefix = getCfg().getString("Prefix").replaceAll("&", "§");
        Player_setMidItem = prefix + getCfg().getString("Player_setMidItem").replaceAll("&", "§");
        Player_setEndItem = prefix + getCfg().getString("Player_setEndItem").replaceAll("&", "§");
        Smith_created = prefix + getCfg().getString("Smith_created").replaceAll("&", "§");
        Smith_Alreadyexists = prefix + getCfg().getString("Smith_Alreadyexists").replaceAll("&", "§");
        Smith_setBrueTime = prefix + getCfg().getString("Smith_setBrueTime").replaceAll("&", "§");
        MustEnterNummber = prefix + getCfg().getString("MustEnterNummber").replaceAll("&", "§");
        Smith_setAcessableJob = prefix + getCfg().getString("Smith_setAcessableJob").replaceAll("&", "§");
        Smith_dosnt_contains_Item = prefix + getCfg().getString("Smith_dosnt_contains_Item").replaceAll("&", "§");
        Smith_removeIngrediends = prefix + getCfg().getString("Smith_removeIngrediends").replaceAll("&", "§");
        Smith_addIngrediends = prefix + getCfg().getString("Smith_addIngrediends").replaceAll("&", "§");
        NeedItemInHand = prefix + getCfg().getString("NeedItemInHand").replaceAll("&", "§");
        Smith_deleted = prefix + getCfg().getString("Smith_deleted").replaceAll("&", "§");
        Smith_dosnt_exists = prefix + getCfg().getString("Smith_dosnt_exists").replaceAll("&", "§");
        Player_collect_Item = prefix + getCfg().getString("Player_collect_Item").replaceAll("&", "§");
        Brue_finished = prefix + getCfg().getString("Brue_finished").replaceAll("&", "§");
        Player_has_wrong_Job = prefix + getCfg().getString("Player_has_wrong_Job").replaceAll("&", "§");
        Brue_cant_start = prefix + getCfg().getString("Brue_cant_start").replaceAll("&", "§");
        Brue_start = prefix + getCfg().getString("Brue_start").replaceAll("&", "§");
        Brue_in_use = prefix + getCfg().getString("Brue_in_use").replaceAll("&", "§");
        Player_cant_craft_item = prefix + getCfg().getString("Player_cant_craft_item").replaceAll("&", "§");
        Player_has_job = prefix + getCfg().getString("Player_has_job").replaceAll("&", "§");
        Player_joined_job = prefix + getCfg().getString("Player_joined_job").replaceAll("&", "§");
        Player_cant_use_Block = prefix + getCfg().getString("Player_cant_use_Block").replaceAll("&", "§");
        PlayerhasnoPermissions  = prefix + getCfg().getString("PlayerhasnoPermissions").replaceAll("&", "§");
        Rasse_exists = prefix + getCfg().getString("Rasse_exists").replaceAll("&", "§");
        Rasse_created = prefix + getCfg().getString("Rasse_created").replaceAll("&", "§");
        Player_isnt_Online = prefix + getCfg().getString("Player_isnt_Online").replaceAll("&", "§");
        Player_deleted_Player_Rasse = prefix + getCfg().getString("Player_deleted_Player_Rasse").replaceAll("&", "§");
        Player_dosnt_have_Rasse = prefix + getCfg().getString("Player_dosnt_have_Rasse").replaceAll("&", "§");
        Rasse_dosnt_exists = prefix + getCfg().getString("Rasse_dosnt_exists").replaceAll("&", "§");
        Rasse_deleted = prefix + getCfg().getString("Rasse_deleted").replaceAll("&", "§");
        Player_set_Player_Rasse = prefix + getCfg().getString("Player_set_Player_Rasse").replaceAll("&", "§");
        Rasse_removed_Effect = prefix + getCfg().getString("Rasse_removed_Effect").replaceAll("&", "§");
        PotionEffect_dosnt_exists = prefix + getCfg().getString("PotionEffect_dosnt_exists").replaceAll("&", "§");
        Potion_effect_added = prefix + getCfg().getString("Potion_effect_added").replaceAll("&", "§");
        NPC_dosnt_exists = prefix + getCfg().getString("NPC_dosnt_exists").replaceAll("&", "§");
        Player_create_NPC = prefix + getCfg().getString("Player_create_NPC").replaceAll("&", "§");
        Player_deleted_NPC = prefix + getCfg().getString("Player_deleted_NPC").replaceAll("&", "§");
        Player_setSpawn_NPC = prefix + getCfg().getString("Player_setSpawn_NPC").replaceAll("&", "§");
        Player_setRasse_NPC  = prefix + getCfg().getString("Player_setRasse_NPC").replaceAll("&", "§");
        Player_setEntityType_NPC = prefix + getCfg().getString("Player_setEntityType_NPC").replaceAll("&", "§");
        Entity_dosnt_exists = prefix + getCfg().getString("Entity_dosnt_exists").replaceAll("&", "§");
        Player_setName_NPC = prefix + getCfg().getString("Player_setName_NPC").replaceAll("&", "§");
        NPC_hasnt_Rasse = prefix + getCfg().getString("NPC_hasnt_Rasse").replaceAll("&", "§");
        Player_setRasse = prefix + getCfg().getString("Player_setRasse").replaceAll("&", "§");
        Player_has_Rasse = prefix + getCfg().getString("Player_has_Rasse").replaceAll("&", "§");
        Player_joined_Rasse = prefix + getCfg().getString("Player_joined_Rasse").replaceAll("&", "§");
        Player_hasnt_Invite = prefix + getCfg().getString("Player_hasnt_Invite").replaceAll("&", "§");
        Player_denyed_Invite = prefix + getCfg().getString("Player_denyed_Invite").replaceAll("&", "§");
        Job_dosnt_exists = prefix + getCfg().getString("Job_dosnt_exists").replaceAll("&", "§");
        Job_already_exists = prefix + getCfg().getString("Job_already_exists").replaceAll("&", "§");
        Job_created = prefix + getCfg().getString("Job_created").replaceAll("&", "§");
        Job_deleted = prefix + getCfg().getString("Job_deleted").replaceAll("&", "§");
        Player_has_default_Job = prefix + getCfg().getString("Player_has_default_Job").replaceAll("&", "§");
        Player_set_Player_default_job = prefix + getCfg().getString("Player_set_Player_default_job").replaceAll("&", "§");
        Player_set_Player_Job = prefix + getCfg().getString("Player_set_Player_Job").replaceAll("&", "§");
        Job_already_containsitem = prefix + getCfg().getString("Job_already_containsitem").replaceAll("&", "§");
        Job_additemtoBlacklist = prefix + getCfg().getString("Job_additemtoBlacklist").replaceAll("&", "§");
        Job_NedditemtoaddtoBlacklist = prefix + getCfg().getString("Job_NedditemtoaddtoBlacklist").replaceAll("&", "§");
        Material_dosnt_exists = prefix + getCfg().getString("Material_dosnt_exists").replaceAll("&", "§");
        Job_dosnt_containsitem = prefix + getCfg().getString("Job_dosnt_containsitem").replaceAll("&", "§");
        Job_removed_item_from_Blacklist = prefix + getCfg().getString("Job_removed_item_from_Blacklist").replaceAll("&", "§");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
