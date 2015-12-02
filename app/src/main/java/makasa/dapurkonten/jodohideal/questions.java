package makasa.dapurkonten.jodohideal;

/**
 * Created by abay on 02/12/15.
 */

        import java.util.Collections;
        import java.util.HashMap;
        import java.util.Map;

public class questions {
    public static final String[] PLANETS = {"sun","mercury","venus","earth","mars","jupiter","saturn","uranus","neptune"};
    public static final Map<String, String> PLANET_DETAIL;
    static {
        Map<String, String> planets = new HashMap<String, String>();
        planets.put("sun", "The Sun is the star at the center of the Solar System. It is almost" +
                " perfectly spherical and consists of hot plasma interwoven with magnetic fields.");
        planets.put("mercury", "Mercury is the smallest and closest to the Sun of the eight planets" +
                " in the Solar System. Mercury's axis has the smallest tilt of any of the planets.");
        planets.put("venus", "Venus is the second planet from the Sun. It has no natural satellite. " +
                "After the Moon, it is the brightest natural object in the night sky.");
        planets.put("earth", "Earth is the third-closest planet to the Sun, the densest planet in " +
                "the Solar System, the largest of the Solar System's four terrestrial planets.");
        planets.put("mars", "Mars is the fourth planet from the Sun and the second smallest planet" +
                " in the Solar System, after Mercury. Mars is a terrestrial planet with a thin atmosphere.");
        planets.put("jupiter", "Jupiter is the fifth planet from the Sun and the largest planet in " +
                "the Solar System. It is a gas giant with mass one-thousandth of that of the Sun.");
        planets.put("saturn", "Saturn is the sixth planet from the Sun and the second largest planet. " +
                "Saturn is a gas giant with an average radius about nine times that of Earth.");
        planets.put("uranus", "Uranus is the seventh planet from the Sun. It has the third-largest" +
                " planetary radius and fourth-largest planetary mass in the Solar System.");
        planets.put("neptune", "Neptune is the eighth and farthest planet from the Sun in the Solar" +
                " System. It is the fourth-largest planet by diameter and the third-largest by mass.");
        PLANET_DETAIL = Collections.unmodifiableMap(planets);
    }
}
