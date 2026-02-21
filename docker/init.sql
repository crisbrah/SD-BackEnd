-- ============================================================
-- Sanidad Divina — Init SQL
-- Inserta los 5 roles del sistema al arrancar la base de datos
-- ============================================================

-- Roles del sistema
INSERT INTO rol (nombre_rol, estado_rol, usua_crea, date_create)
VALUES
    ('SUPER_ADMIN', true, 'system', NOW()),
    ('ADMIN',       true, 'system', NOW()),
    ('TESORERO',    true, 'system', NOW()),
    ('ENCARGADO',   true, 'system', NOW()),
    ('MAESTRO',     true, 'system', NOW()),
    ('MIEMBRO',     true, 'system', NOW())
ON CONFLICT (nombre_rol) DO NOTHING;
