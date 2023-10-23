package me.jishuna.paintbrush.brushdata;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.jishlib.pdc.PersistentTypes;
import me.jishuna.paintbrush.dye.Dye;
import me.jishuna.paintbrush.dye.DyeRegistry;

public class PaintbrushDataType implements PersistentDataType<PersistentDataContainer, PaintbrushData> {
    public static final PaintbrushDataType INSTANCE = new PaintbrushDataType();

    private static final NamespacedKey COLOR_KEY = NamespacedKey.fromString("paintbrush:color");
    private static final NamespacedKey USES_KEY = NamespacedKey.fromString("paintbrush:uses");

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public Class<PaintbrushData> getComplexType() {
        return PaintbrushData.class;
    }

    @Override
    public PersistentDataContainer toPrimitive(PaintbrushData complex, PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        if (complex.hasDye()) {
            container.set(COLOR_KEY, PersistentTypes.STRING, complex.getDye().getName());
            container.set(USES_KEY, PersistentTypes.INTEGER, complex.getUses());
        }
        return container;
    }

    @Override
    public PaintbrushData fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        Dye dye = DyeRegistry.getDye(primitive.get(COLOR_KEY, PersistentTypes.STRING));
        int uses = primitive.getOrDefault(USES_KEY, PersistentTypes.INTEGER, 0);

        return new PaintbrushData(dye, uses);
    }
}
