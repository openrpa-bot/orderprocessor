CREATE TABLE IF NOT EXISTS om_Tenant_Config (
    tenantName VARCHAR(100) PRIMARY KEY NOT NULL,
    port INT NOT NULL,
    isActive BOOLEAN NOT NULL
);


INSERT INTO om_Tenant_Config (tenantName, port, isActive)
SELECT 'shoonya', 5000, FALSE
WHERE NOT EXISTS (SELECT 1 FROM om_Tenant_Config);

INSERT INTO om_Tenant_Config (tenantName, port, isActive)
SELECT 'angelone', 5000, FALSE
WHERE NOT EXISTS (SELECT 1 FROM om_Tenant_Config WHERE tenantName = 'angelone');

INSERT INTO om_Tenant_Config (tenantName, port, isActive)
SELECT 'zerodha', 5000, FALSE
WHERE NOT EXISTS (SELECT 1 FROM om_Tenant_Config WHERE tenantName = 'zerodha');
