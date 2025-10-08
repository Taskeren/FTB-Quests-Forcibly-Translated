package cn.elytra.mod.forcibly_translated.mixin;

import cn.elytra.mod.forcibly_translated.ForciblyTranslated;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestButton;
import dev.ftb.mods.ftbquests.quest.Quest;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = QuestButton.class, remap = false)
public class MixinQuestButton {

    @Redirect(
        method = "<init>", at = @At(
        value = "INVOKE",
        target = "Ldev/ftb/mods/ftbquests/quest/Quest;getTitle()Lnet/minecraft/network/chat/Component;"))
    private static Component forcibly_translated$redirectGetTitle(Quest instance) {
        return ForciblyTranslated.tryGetComponent(ForciblyTranslated.keyForQuestTitle(instance), instance::getTitle);
    }

    @Redirect(
        method = "addMouseOverText",
        at = @At(
            value = "INVOKE",
            target = "Ldev/ftb/mods/ftbquests/quest/Quest;getSubtitle()Lnet/minecraft/network/chat/Component;"))
    private Component forcibly_translated$redirectGetSubtitle(Quest instance) {
        return ForciblyTranslated.tryGetComponent(
            ForciblyTranslated.keyForQuestSubtitle(instance),
            instance::getSubtitle);
    }

}
