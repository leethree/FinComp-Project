#!/bin/sh

fc() {
    java -cp bin hk.hku.cs.c7802.driver.Main "$@"
}

market_rate() {
    fc -y -s curveSpec.csv -i curveDataInput.csv 2013-01-22
}

testYieldCurve() {
    fc -y -s curveSpec.csv -i curveDataInput.csv > yieldCurve.csv
    echo "yieldCurve.csv"
}

testBSGetSigma() {
    fc -bs -e -S 89.31 -E 2013-01-22 -r $MARKET_RATE -v 30 call 95
}

testBSGetCall() {
    fc -bs -e -S 89.31 -E 2013-01-22 -r $MARKET_RATE -s $SIGMA call 95
}

testBTGetCall() {
    fc -bt -e -S 89.31 -E 2013-01-22 -r $MARKET_RATE -s $SIGMA call 95
}

testBTGetOptionA() {
    # American
    fc -bt -a -S 89.31 -E 2013-01-22 -r $MARKET_RATE -s $SIGMA A
}

testMCGetCall() {
    fc -mc -e -S 89.31 -E 2013-01-22 -r $MARKET_RATE -s $SIGMA call 95
}

testMCGetOptionAEuro() {
    fc -mc -e -S 89.31 -E 2013-01-22 -r $MARKET_RATE -s $SIGMA A
}

testMCGetOptionB() {
    fc -mc -e -S 89.31 -E 2013-01-22 -r $MARKET_RATE -s $SIGMA B
}

if [ $# -gt 0 ]; then
    fc "$@"
else

MARKET_RATE=$(market_rate 2013-01-22)
if [ "$MARKET_RATE" = "" ]; then
MARKET_RATE=0.04763372229424285 
fi
SIGMA=0.6377602538096591 

# Yield Curve
echo -n 'testYieldCurve = '
testYieldCurve

# BS
echo -n 'testBSGetSigma = '
testBSGetSigma 
echo -n 'testBSGetCall = '
testBSGetCall

# BT
echo -n 'testBTGetCall = '
testBTGetCall
echo -n 'testBTGetOptionA = '
testBTGetOptionA
# MC
echo -n 'testMCGetCall = '
testMCGetCall
echo -n 'testMCGetOptionAEuro = '
testMCGetOptionAEuro
echo -n 'testMCGetOptionB = '
testMCGetOptionB
fi
