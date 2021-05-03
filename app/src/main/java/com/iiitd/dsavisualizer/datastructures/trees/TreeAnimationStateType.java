package com.iiitd.dsavisualizer.datastructures.trees;

// ENUM Class
// Used in Tree Animation for knowing the type of animation type
public enum TreeAnimationStateType {
    NO_SPACE,                 // NS
    NOT_FOUND,                // NF
    FOUND,                    // F
    ORDER_TRAVERSAL,          // P
    SEARCH,                   // S
    INSERT,                   // I
    DELETE_1_CHILD,           // D
    DELETE_DECREASE,          // C
    DELETE_NO_CHILD,          // 1
    COPY_AND_MOVE,            // CM
    MOVE_BACK,                // MB
    ROTATION,                 // R
    NULL                      // N
}