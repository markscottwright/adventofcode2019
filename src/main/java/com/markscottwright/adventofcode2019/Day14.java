package com.markscottwright.adventofcode2019;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 {
    private static final long ONE_TRILLION = 1_000_000_000_000L;
    final static String INPUT =
    // @formatter:off
            "156 ORE => 6 TLFQZ\r\n" + 
            "1 SZFV => 5 TNXGD\r\n" + 
            "1 BQLJ, 3 VNKPF => 8 BQXZ\r\n" + 
            "43 FPRFS, 5 CQJT, 20 LDKTQ, 48 ZPBLH, 21 MFVG, 43 WLWZQ, 1 ZWZQ, 11 PQZJP, 56 CTJGD, 35 SGDVW => 1 FUEL\r\n" + 
            "11 BQXZ, 1 PRCSN => 7 DVFD\r\n" + 
            "7 VWXB, 2 PRCSN, 24 VJSR, 9 MDWCG, 4 MFVG => 4 ZWZQ\r\n" + 
            "32 BQXZ, 5 XDSHP, 16 KTXJR, 7 VJSR, 3 MDWCG, 11 KZFZG, 3 NVBN => 5 ZPBLH\r\n" + 
            "2 BQLJ, 2 RSKH, 3 VWXB => 2 GWXCF\r\n" + 
            "6 PRCSN, 1 NCRZ => 8 VJSR\r\n" + 
            "5 TMQLD => 9 VDQL\r\n" + 
            "9 MZQZS, 1 FLRB => 5 BQLJ\r\n" + 
            "4 KLHS => 5 PQZJP\r\n" + 
            "1 WJTS, 1 NCRZ, 27 XDSHP => 8 MFVG\r\n" + 
            "1 FNXMV, 30 FPKM => 8 RDMDL\r\n" + 
            "1 TNXGD, 21 XBCLW, 5 CWNV => 3 RSKH\r\n" + 
            "4 KQFPJ => 2 NCRZ\r\n" + 
            "10 CWNV, 8 HSXW => 9 FNXMV\r\n" + 
            "2 TNXGD, 4 CWNV, 13 VJSR => 8 KTXJR\r\n" + 
            "3 NCRZ, 1 GWXCF, 8 NVBN, 6 MDWCG, 3 VWXB, 4 KTXJR, 4 DVFD, 3 QXCV => 9 FPRFS\r\n" + 
            "5 MZQZS, 9 TBVRN => 7 SZFV\r\n" + 
            "37 GWXCF, 15 RDMDL, 2 MDWCG => 7 CQJT\r\n" + 
            "1 VDQL, 2 HSXW => 4 NVBN\r\n" + 
            "18 QHMTL, 7 FLRB, 1 SZFV => 3 FPKM\r\n" + 
            "6 VDQL => 1 FNCN\r\n" + 
            "3 QPHT => 7 LDKTQ\r\n" + 
            "1 TLFQZ => 8 FWFR\r\n" + 
            "7 VDQL, 8 KZFZG => 3 HSXW\r\n" + 
            "9 TBVRN => 7 MZQZS\r\n" + 
            "1 FLRB, 44 VNKPF, 1 LVZF => 8 QXCV\r\n" + 
            "1 WLWZQ, 3 TBVRN, 4 TLFQZ => 9 KQFPJ\r\n" + 
            "1 BQLJ, 1 PRCSN, 8 DHTNG => 5 VWXB\r\n" + 
            "1 XDSHP, 6 NVBN => 1 BDGC\r\n" + 
            "8 PRCSN, 1 DHTNG => 2 WJTS\r\n" + 
            "19 DHTNG, 22 WLWZQ => 9 LVZF\r\n" + 
            "185 ORE => 7 WLWZQ\r\n" + 
            "1 TMQLD, 1 MZQZS => 8 KZFZG\r\n" + 
            "111 ORE => 4 TBVRN\r\n" + 
            "31 VDQL, 14 MZQZS => 7 XBCLW\r\n" + 
            "6 VDQL, 3 KVPK => 9 SGDVW\r\n" + 
            "1 FNCN => 6 QMKT\r\n" + 
            "1 FNCN, 3 TMQLD => 7 VNKPF\r\n" + 
            "2 QPHT => 6 VQXCJ\r\n" + 
            "2 LDKTQ, 3 VQXCJ => 5 FLRB\r\n" + 
            "1 FNCN, 3 FPKM, 1 SZFV => 2 DHTNG\r\n" + 
            "1 KZFZG => 9 QHMTL\r\n" + 
            "141 ORE => 5 QPHT\r\n" + 
            "16 TNXGD => 9 CWNV\r\n" + 
            "1 KQFPJ, 29 FWFR => 2 KVPK\r\n" + 
            "1 TNXGD, 7 KLHS => 2 XDSHP\r\n" + 
            "7 WJTS => 6 MDWCG\r\n" + 
            "3 BDGC, 3 XDSHP, 1 NCRZ => 4 CTJGD\r\n" + 
            "3 QMKT => 6 PRCSN\r\n" + 
            "24 FWFR => 2 TMQLD\r\n" + 
            "8 VNKPF => 3 KLHS\r\n";
            // @formatter:on

    static class ChemicalQuantity {
        public final String name;
        public final int amount;

        public ChemicalQuantity(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return amount + " " + name;
        }

        static Pattern pattern = Pattern
                .compile("\\s*([0-9]+)\\s+([A-Z]+)\\s*");

        static ChemicalQuantity parse(String s) {
            var m = pattern.matcher(s);
            m.matches();
            return new ChemicalQuantity(m.group(2),
                    Integer.parseInt(m.group(1)));
        }
    };

    static class Reaction {
        final public ChemicalQuantity output;
        final public HashSet<ChemicalQuantity> inputs;

        public Reaction(ChemicalQuantity output,
                Collection<ChemicalQuantity> inputs) {
            this.output = output;
            this.inputs = new HashSet<>(inputs);
        }

        static Reaction parse(String s) {
            String[] inputsAndOutput = s.split("=>");
            var output = ChemicalQuantity.parse(inputsAndOutput[1]);
            var inputs = Arrays.stream(inputsAndOutput[0].split(","))
                    .map(ChemicalQuantity::parse).collect(Collectors.toSet());
            return new Reaction(output, inputs);
        }

        static Set<Reaction> parseReactions(String input) {
            return Arrays.stream(input.split("\\r?\\n")).map(Reaction::parse)
                    .collect(Collectors.toSet());
        }

        @Override
        public String toString() {
            return inputs + " => " + output;
        }

        static Map<String, Reaction> toRecipes(Collection<Reaction> src) {
            Map<String, Reaction> out = new HashMap<String, Reaction>();
            src.forEach(r -> {
                assert out.get(r.output.name) == null;
                out.put(r.output.name, r);
            });
            return out;
        }
    }

    public static void main(String[] args) {
        System.out.print("Day 14 part 1: ");
        Set<Reaction> reactions = Reaction.parseReactions(INPUT);
        var recipes = Reaction.toRecipes(reactions);
        System.out.println(new Solution(recipes).solveForFuel());

        // close in on solution = should be O(log(ONE_TRILLION))
        System.out.print("Day 14 part 2: ");
        long low = 0, high = ONE_TRILLION, guess = 0;
        while (low < high - 1) {
            guess = low + (high - low) / 2L;
            long oreConsumed = new Solution(recipes).solveForFuel(guess);
            if (oreConsumed < ONE_TRILLION)
                low = guess;
            else
                high = guess;
        }
        System.out.println(guess);
    }

    /**
     * This feels hacky as hell. I bet I'm supposed to do some sort of
     * constraint solving.
     */
    static class Solution {
        private final Map<String, Reaction> recipes;
        private HashMap<String, Long> intermediates = new HashMap<>();
        private long oreConsumed = 0;

        public Solution(Map<String, Reaction> recipes) {
            this.recipes = recipes;
        }

        public long solveForFuel(long guess) {
            intermediates.clear();
            oreConsumed = 0;
            build("FUEL", guess);
            return oreConsumed;
        }

        private void build(String chemical, long required) {
            var recipe = recipes.get(chemical);
            if (chemical.equals("ORE"))
                oreConsumed += required;
            else {
                long alreadyMade = intermediates.getOrDefault(chemical, 0L);
                if (alreadyMade >= required)
                    intermediates.put(chemical, alreadyMade - required);
                else {
                    required -= alreadyMade;
                    intermediates.put(chemical, 0L);

                    long reactionsRequired, leftOver;
                    if (required % recipe.output.amount == 0) {
                        leftOver = 0;
                        reactionsRequired = required / recipe.output.amount;
                    } else {
                        leftOver = recipe.output.amount
                                - (required % recipe.output.amount);
                        reactionsRequired = required / recipe.output.amount + 1;
                    }
                    for (var input : recipe.inputs) {
                        build(input.name, input.amount * reactionsRequired);
                    }
                    intermediates.put(chemical,
                            intermediates.getOrDefault(chemical, 0L)
                                    + leftOver);
                }
            }
        }

        long solveForFuel() {
            return solveForFuel(1);
        }
    };
}
