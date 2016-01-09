package de.netpage.markdown.statistic;

import de.netpage.markdown.model.Statistic;

/**
 * Dies ist das Interface zum Schreiben von Statistiken.
 * Zum Schrbeiben der Statistiken gibt es zwei Implementierungen:
 * - Log4jStatisticPrinter
 * - PrintStreamStatisticPrinter
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public interface StatisticPrinter {

    /**
     * Liefert ein Ausgabeformat der Statistik zurück.
     *
     * @param statistic Statisitik, die ausgegebene werden soll
     */
    void print(Statistic statistic);

}
