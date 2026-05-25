package com.kwwsyk.suit.skin.neoforge;

import com.kwwsyk.suit.common.options.config.ComplexConfigEntryImpl;
import com.kwwsyk.suit.common.options.config.ConfigEntryImpl;
import com.kwwsyk.suit.skin.config.ClientConfigs;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Supplier;

public class ConfigInstaller {

    private ModConfigSpec modConfigSpec;

    //public final ModConfigSpec.ListValueSpec SMC;
    public static ModConfigSpec init(List<ConfigEntryImpl<?>> configs){
        try {
            Pair<ConfigInstaller, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(builder -> new ConfigInstaller(builder, configs));
            pair.getLeft().modConfigSpec = pair.getRight();
            return pair.getRight();
        } catch (Exception e) {
            throw new RuntimeException("Exception in initialize suit config: " ,e);
        }
    }

    public ConfigInstaller(ModConfigSpec.Builder builder, List<ConfigEntryImpl<?>> configs){
        configs.forEach(cfg->{
            cfg.setSaver(() -> modConfigSpec.save());//don't change it to method reference; else NPE thrown
            recursiveBuild(builder,cfg);
        });
    }

    @SuppressWarnings({"unchecked", "raw_usage"})
    private static <T, E extends Enum<E>> void recursiveBuild(ModConfigSpec.Builder builder, ConfigEntryImpl<T> cfg){
        switch (cfg){
            case ConfigEntryImpl.BooleanEntry booleanEntry -> {
                var configValue = builder.comment(booleanEntry.comments()).define(booleanEntry.key(), booleanEntry.defaultValue());
                booleanEntry.initialize(configValue,configValue::set);
            }
            case ConfigEntryImpl.IntEntry intEntry -> {
                var cv = builder.comment(intEntry.comments()).defineInRange(intEntry.key(), intEntry.defaultValue(), intEntry.getMin(), intEntry.getMax());
                intEntry.initialize(cv,cv::set);
            }
            case ConfigEntryImpl.StringEntry stringEntry -> {
                var cv = builder.comment(stringEntry.comments()).define(stringEntry.key(), stringEntry.defaultValue());
                stringEntry.initialize(cv,cv::set);
            }
            case ConfigEntryImpl.DoubleEntry doubleEntry -> {
                var cv = builder.comment(doubleEntry.comments()).defineInRange(doubleEntry.key(), doubleEntry.defaultValue(), doubleEntry.getMin(), doubleEntry.getMax());
                doubleEntry.initialize(cv,cv::set);
            }
            case ConfigEntryImpl.EnumEntry<?> enumEntry -> {//how to deal with the recursive typed param: E extends Enum<E extends Enum<E...>>? /*HERE the (E) transformation is necessary, else compile errors, but IDEA doesn't*/I finally tried wip out Types
                ModConfigSpec.ConfigValue<T> cv = (ModConfigSpec.ConfigValue<T>) builder.comment(enumEntry.comments()).defineEnum(enumEntry.key(),(E)enumEntry.defaultValue());
                cfg.initialize(cv, cv::set);
            }
            case ConfigEntryImpl.ListEntry<?> listEntry -> {
                var cv = builder.comment(listEntry.comments()).defineListAllowEmpty(listEntry.key(),listEntry.defaultValue(),(Supplier)listEntry.getNewValSupplier(),listEntry.getNewValPredicate());
                listEntry.initialize(cv, cv::set);
            }
            case ConfigEntryImpl.FloatEntry floatEntry -> {
                var cv = builder.comment(floatEntry.comments()).defineInRange(floatEntry.key(), floatEntry.defaultValue(), floatEntry.getMin(), floatEntry.getMax());
                floatEntry.initialize(()-> cv.get().floatValue(), f -> cv.set((double) f));
            }
            case ConfigEntryImpl.LongEntry longEntry -> {
                var cv = builder.comment(longEntry.comments()).defineInRange(longEntry.key(), longEntry.defaultValue(), longEntry.getMin(), longEntry.getMax());
                longEntry.initialize(cv,cv::set);
            }
            case ComplexConfigEntryImpl<?> complexEntry -> {
                var cmt = complexEntry.comments();
                if(cmt.length != 0){
                    builder.comment(cmt);
                    builder.comment("-----");
                }
                builder.push(complexEntry.key());

                for(ConfigEntryImpl<?> field : complexEntry.fields()) recursiveBuild(builder,field);
                complexEntry.setInitialized();
                builder.pop();
            }
            default -> {
                if(cfg instanceof ComplexConfigEntryImpl<?> complexEntry){
                    builder.push(complexEntry.key());
                    var cmt = complexEntry.comments();
                    if(cmt.length != 0){
                        builder.comment(cmt);
                        builder.comment("-----");
                    }
                    for(ConfigEntryImpl<?> field : complexEntry.fields()) recursiveBuild(builder,field);
                    complexEntry.setInitialized();
                    builder.pop();
                }else {
                    ModConfigSpec.ConfigValue<T> cv = builder.comment(cfg.comments()).define(cfg.key(), cfg.defaultValue());//good luck to you
                    cfg.initialize(cv, cv::set);
                }
            }
        }
    }
}
