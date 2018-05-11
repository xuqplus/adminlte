ALTER TABLE adminlte.user
  ADD COLUMN email VARCHAR(255);
ALTER TABLE user
  ADD CONSTRAINT UK_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);