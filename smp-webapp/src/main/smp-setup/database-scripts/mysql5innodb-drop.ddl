-- ------------------------------------------------------------------------
-- This file was generated by hibernate for SMP version 1.3.0.
-- ------------------------------------------------------------------------


    alter table SMP_ALERT_AUD
       drop
       foreign key FKrw0qnto448ojlirpfmfntd8v2;

    alter table SMP_ALERT_PROPERTY
       drop
       foreign key FK15r37w3r5ty5f6074ykr2o4i6;

    alter table SMP_ALERT_PROPERTY_AUD
       drop
       foreign key FKod33qjx87ih1a0skxl2sgddar;

    alter table SMP_CERTIFICATE
       drop
       foreign key FKayqgpj5ot3o8vrpduul7sstta;

    alter table SMP_CERTIFICATE_AUD
       drop
       foreign key FKnrwm8en8vv10li8ihwnurwd9e;

    alter table SMP_CONFIGURATION_AUD
       drop
       foreign key FKd4yhbdlusovfbdti1fjkuxp9m;

    alter table SMP_DOMAIN_AUD
       drop
       foreign key FK35qm8xmi74kfenugeonijodsg;

    alter table SMP_OWNERSHIP
       drop
       foreign key FKrnqwq06lbfwciup4rj8nvjpmy;

    alter table SMP_OWNERSHIP
       drop
       foreign key FKgexq5n6ftsid8ehqljvjh8p4i;

    alter table SMP_OWNERSHIP_AUD
       drop
       foreign key FK1lqynlbk8ow1ouxetf5wybk3k;

    alter table SMP_SERVICE_GROUP_AUD
       drop
       foreign key FKj3caimhegwyav1scpwrxoslef;

    alter table SMP_SERVICE_GROUP_DOMAIN
       drop
       foreign key FKo186xtefda6avl5p1tuqchp3n;

    alter table SMP_SERVICE_GROUP_DOMAIN
       drop
       foreign key FKgcvhnk2n34d3c6jhni5l3s3x3;

    alter table SMP_SERVICE_GROUP_DOMAIN_AUD
       drop
       foreign key FK6uc9r0eqw16baooxtmqjkih0j;

    alter table SMP_SERVICE_METADATA
       drop
       foreign key FKfvcml6b8x7kn80m30h8pxs7jl;

    alter table SMP_SERVICE_METADATA_AUD
       drop
       foreign key FKbqr9pdnik1qxx2hi0xn4n7f61;

    alter table SMP_SERVICE_METADATA_XML
       drop
       foreign key FK4b1x06xlavcgbjnuilgksi7nm;

    alter table SMP_SERVICE_METADATA_XML_AUD
       drop
       foreign key FKevatmlvvwoxfnjxkvmokkencb;

    alter table SMP_SG_EXTENSION
       drop
       foreign key FKtf0mfonugp2jbkqo2o142chib;

    alter table SMP_SG_EXTENSION_AUD
       drop
       foreign key FKmdo9v2422adwyebvl34qa3ap6;

    alter table SMP_USER_AUD
       drop
       foreign key FK2786r5minnkai3d22b191iiiq;

    drop table if exists SMP_ALERT;

    drop table if exists SMP_ALERT_AUD;

    drop table if exists SMP_ALERT_PROPERTY;

    drop table if exists SMP_ALERT_PROPERTY_AUD;

    drop table if exists SMP_CERTIFICATE;

    drop table if exists SMP_CERTIFICATE_AUD;

    drop table if exists SMP_CONFIGURATION;

    drop table if exists SMP_CONFIGURATION_AUD;

    drop table if exists SMP_DOMAIN;

    drop table if exists SMP_DOMAIN_AUD;

    drop table if exists SMP_OWNERSHIP;

    drop table if exists SMP_OWNERSHIP_AUD;

    drop table if exists SMP_REV_INFO;

    drop table if exists SMP_SERVICE_GROUP;

    drop table if exists SMP_SERVICE_GROUP_AUD;

    drop table if exists SMP_SERVICE_GROUP_DOMAIN;

    drop table if exists SMP_SERVICE_GROUP_DOMAIN_AUD;

    drop table if exists SMP_SERVICE_METADATA;

    drop table if exists SMP_SERVICE_METADATA_AUD;

    drop table if exists SMP_SERVICE_METADATA_XML;

    drop table if exists SMP_SERVICE_METADATA_XML_AUD;

    drop table if exists SMP_SG_EXTENSION;

    drop table if exists SMP_SG_EXTENSION_AUD;

    drop table if exists SMP_USER;

    drop table if exists SMP_USER_AUD;
