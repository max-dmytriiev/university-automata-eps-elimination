package com.novytech.university.automata;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Transition implements Comparable<Transition> {
    private final State source;
    private final Symbol activator;
    private final State target;

    @Override
    public String toString() {
        return new StringBuilder()
                .append(source)
                .append(", ")
                .append(activator.getSymbol())
                .append(" --> ")
                .append(target).toString();
    }

    @Override
    public int compareTo(Transition o) {
        int sourceCompare = source.compareTo(o.source);
        if (sourceCompare == 0) {
            int activatorCompare = activator.compareTo(o.activator);
            if (activatorCompare == 0) {
                int targetCompare = target.compareTo(o.target);
                return targetCompare;
            }
            return activatorCompare;
        }
        return sourceCompare;
    }
}
