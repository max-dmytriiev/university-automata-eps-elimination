package com.novytech.university.superautomata;

import com.novytech.university.automata.Symbol;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class SuperTransition {
    private final SuperState source;
    private final Symbol activator;
    private final SuperState target;

    @Override
    public String toString() {
        return new StringBuilder()
                .append(source)
                .append(", ")
                .append(activator.getSymbol())
                .append(" --> ")
                .append(target)
                .toString();
    }
}
