package com.report.reportProject.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    /* JDBC ekleyeceğiz ve verilerimizi databaseden alacağız
    onceden belirlemiş oldugum SQL verileri ile işlem yapılabilir

    USE `lab_reporting_db`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Kullanılan şifre : 1234
--

INSERT INTO `users`
VALUES
('standart','{bcrypt}$2a$10$DJrjpequQOb3XkBmpKL..u4Xeh3YtUR2ii7iqooQLVP296s6Zs8Ta',1),
('moderator','{bcrypt}$2a$10$DJrjpequQOb3XkBmpKL..u4Xeh3YtUR2ii7iqooQLVP296s6Zs8Ta',1),
('admin','{bcrypt}$2a$10$DJrjpequQOb3XkBmpKL..u4Xeh3YtUR2ii7iqooQLVP296s6Zs8Ta',1);


CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `authorities`
VALUES
('standart','ROLE_STANDART'),
('moderator','ROLE_STANDART'),
('moderator','ROLE_MODERATOR'),
('admin','ROLE_STANDART'),
('admin','ROLE_MODERATOR'),
('admin','ROLE_ADMIN');
     */


    /* BCRYPT password işlemi Tek yönlü bir şifrelemedir birkez dönüştürüldükten sonra tekrar geri dönuş sağlanılamaz
    bununla birlikte database de crypt edilmiş şifre tutulur yani databasemiz hacklendiğinde ele geçirilen şifreler korunmuş olucaktır

     Spring boot jdbc ile Auth da istenen şifreyi BCRYPT işlemini uygulayarak database ile otomatik olarak karşılaştırır
     eğer eşleşme sağlanırsa erişim elde edilir
     
    butun kullanıcıların şifresi : 1234

    $2a$10$DJrjpequQOb3XkBmpKL..u4Xeh3YtUR2ii7iqooQLVP296s6Zs8Ta -> 1234 bu dönuşumu spring boot jdbc ile otomatik olarak yapacaktır
     */


    @Bean // otomatik olarak verilerimizi inject edecek bir spring boot özelliği
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
        // bu fonksiyon spring security JDBC authentication kullanacagımızı söyler
        // Auth için databasedeki tabloları ve kolonları otomatik tanımlar
        // böylece elimizle buraya kod sayfasına data eklemek zorunda değiliz SQL kullanarak veri ekleyebiliriz
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
        web tabanlı guvenlik yapılarını kullanacağız bu işlemde

         */
        http.authorizeRequests(configurer -> // burası bir lamda ifadesi fonksiyonları birbiri ardına çağırıp aralarında bilgi aktarımını sağlar
                configurer// burada yaptıgımız işlem formlardan gelen veya url lerden gelen methodların guvenlik derecelerine
                        //göre sınıflandırmak oldu eşleşen methodlar rollere sahip oldugu taktirde işlem gerçekleşecektir aksi taktirde
                        // uygulama 401 hatası veya 403 forbiden ile hata verecektir
                        .requestMatchers(HttpMethod.GET,"/api/laborants").hasRole("STANDART")
                        .requestMatchers(HttpMethod.GET,"/api/laborants/**").hasRole("STANDART")
                        .requestMatchers(HttpMethod.GET,"/api/reports").hasRole("STANDART")
                        .requestMatchers(HttpMethod.GET,"/api/reports/**").hasRole("STANDART")

                        .requestMatchers(HttpMethod.POST,"/api/laborants").hasRole("MODERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/reports").hasRole("MODERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/reports/**").hasRole("MODERATOR")

                        .requestMatchers(HttpMethod.PUT,"/api/laborants/**").hasRole("MODERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/reports/**").hasRole("MODERATOR")

                        .requestMatchers(HttpMethod.DELETE,"/api/laborants/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/reports/**").hasRole("ADMIN")



        );
        // HTTP nin basit authentication kullancagını belirleyelim
        // bizden basit bir username ve password isteyecektir
        http.httpBasic(Customizer.withDefaults());

        // CSRF web uygulamaları için güvenlik sağlar fakat rest apiler için devredışı bırakmamız gerekirmiş
        http.csrf(csrf -> csrf.disable());

        return http.build(); // security filter chain olusturup döndurur
    }

}
