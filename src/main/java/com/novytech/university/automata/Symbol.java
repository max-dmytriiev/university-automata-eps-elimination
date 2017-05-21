package com.novytech.university.automata;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Symbol implements Comparable<Symbol> {
    public static final Symbol EPS = new Symbol("EPS");

    private final String symbol;

    @Override
    public int compareTo(Symbol o) {
        return symbol.compareTo(o.symbol);
    }
}
