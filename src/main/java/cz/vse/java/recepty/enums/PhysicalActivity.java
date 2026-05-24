package cz.vse.java.recepty.enums;
/**
 * Výčtový typ reprezentující fyzickou aktivitu uživatele.
 * Tyto informace doplňují typ zaměstnání a pomáhají přesněji vypočítat
 * celkový denní energetický výdej (TDEE) a potřebu makroživin.
 *
 * @author Vojtěch Soldán, Lukáš Kucharík
 * @version 0.2 (17. 5. 2026)
 */
public enum PhysicalActivity {
    SEDENTARY,      // Sedavý způsob života (žádná nebo minimální fyzická aktivita)
    LIGHT,          // Lehká aktivita (např. chůze, lehké domácí práce) 1-3 dny v týdnu
    MODERATE,       // Střední aktivita (např. mírné cvičení, sport) 3-5 dní v týdnu
    ACTIVE,         // Aktivní (např. intenzivní cvičení, sport) 6-7 dní v týdnu
    VERY_ACTIVE,    // Velmi aktivní (např. těžké cvičení, fyzická práce) každý den nebo vícekrát denně
    EXTRA_ACTIVE    // Velmi intenzivní denní aktivita (např. profesionální sportovci, velmi těžká fyzická práce)
}
