insert into smp_user(username, password, pat_id, pat_value, isadmin) values ('test_admin','$2a$06$k.Q/6anG4Eq/nNTZ0C1UIuAKxpr6ra5oaMkMSrlESIyA5jKEsUdyS', 'test_pat_admin','$2a$10$bP44Ij/mE6U6OUo/QrKCvOb7ouSClKnyE0Ak6t58BLob9OTI534IO', 1);
insert into smp_user(username, password, pat_id, pat_value, isadmin) values ('test_user_hashed_pass','$2a$06$k.Q/6anG4Eq/nNTZ0C1UIuAKxpr6ra5oaMkMSrlESIyA5jKEsUdyS','test_pat_hashed_pass','$2a$10$WftDXn7YqMI/15D8r6fMOOHGQOPxAin8BwQJOjDe1d66SkEuekJ5q', 0);
insert into smp_user(username, password, pat_id, pat_value, isadmin) values ('test_user_clear_pass', 'gutek123','test_user_clear_pass', 'gutek123',0);
insert into smp_user(username, password, pat_id, pat_value, isadmin) values ('CN=common name,O=org,C=BE:0000000000000066', 'CN=common name,O=org,C=BE:0000000000000066', '',0);
