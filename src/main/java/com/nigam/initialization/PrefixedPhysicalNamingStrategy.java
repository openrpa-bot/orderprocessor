package com.nigam.initialization;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PrefixedPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 1L;

    private static final String TABLE_PREFIX = "op_"; // <-- change this prefix

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        if (name == null) return null;

        String newName = TABLE_PREFIX + name.getText();
        return Identifier.toIdentifier(newName);
    }
}

