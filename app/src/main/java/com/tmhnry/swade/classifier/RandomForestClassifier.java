package com.tmhnry.swade.classifier;

public class RandomForestClassifier {
    public static int predict_0(double[] features) {
        int[] classes = new int[2];
        
        if (features[26] <= 2.5) {
            if (features[6] <= 1.5) {
                if (features[0] <= 1.5) {
                    if (features[29] <= 39.5) {
                        classes[0] = 0; 
                        classes[1] = 14; 
                    } else {
                        if (features[5] <= 2.0) {
                            classes[0] = 0; 
                            classes[1] = 4; 
                        } else {
                            classes[0] = 5; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[14] <= 8964.5) {
                        if (features[21] <= 1.5) {
                            if (features[9] <= 3.5) {
                                if (features[3] <= 8.5) {
                                    if (features[26] <= 1.0) {
                                        if (features[7] <= 0.5) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 6; 
                                        }
                                    } else {
                                        if (features[24] <= 2.5) {
                                            classes[0] = 7; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[4] <= 3.0) {
                                                if (features[1] <= 1011.0) {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                } else {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                }
                                            } else {
                                                classes[0] = 3; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    }
                                } else {
                                    if (features[8] <= 38.0) {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 16; 
                                    }
                                }
                            } else {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            }
                        } else {
                            classes[0] = 9; 
                            classes[1] = 0; 
                        }
                    } else {
                        classes[0] = 13; 
                        classes[1] = 0; 
                    }
                }
            } else {
                if (features[29] <= 31.5) {
                    if (features[22] <= 2.5) {
                        if (features[13] <= 0.5) {
                            classes[0] = 5; 
                            classes[1] = 0; 
                        } else {
                            if (features[7] <= 0.5) {
                                if (features[8] <= 53.0) {
                                    classes[0] = 0; 
                                    classes[1] = 5; 
                                } else {
                                    if (features[23] <= 4.5) {
                                        if (features[18] <= 11.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 3; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 5; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                if (features[18] <= 13.5) {
                                    classes[0] = 0; 
                                    classes[1] = 9; 
                                } else {
                                    if (features[3] <= 19.5) {
                                        if (features[5] <= 2.5) {
                                            if (features[23] <= 3.5) {
                                                if (features[3] <= 1.5) {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 4; 
                                                }
                                            } else {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 3; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    }
                                }
                            }
                        }
                    } else {
                        if (features[21] <= 0.5) {
                            if (features[18] <= 17.5) {
                                if (features[11] <= 7.0) {
                                    if (features[22] <= 5.0) {
                                        classes[0] = 5; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[5] <= 4.0) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            }
                        } else {
                            if (features[17] <= 0.5) {
                                classes[0] = 30; 
                                classes[1] = 0; 
                            } else {
                                if (features[1] <= 591.5) {
                                    if (features[15] <= 14032.5) {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 4; 
                                    }
                                } else {
                                    classes[0] = 10; 
                                    classes[1] = 0; 
                                }
                            }
                        }
                    }
                } else {
                    if (features[17] <= 0.5) {
                        if (features[23] <= 5.5) {
                            if (features[1] <= 377.5) {
                                if (features[3] <= 20.5) {
                                    if (features[19] <= 3.5) {
                                        classes[0] = 16; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[14] <= 3710.0) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            } else {
                                if (features[29] <= 32.5) {
                                    if (features[5] <= 3.0) {
                                        classes[0] = 6; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                } else {
                                    classes[0] = 74; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[25] <= 4.0) {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            } else {
                                classes[0] = 1; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[25] <= 2.5) {
                            if (features[15] <= 16626.5) {
                                if (features[7] <= 0.5) {
                                    if (features[18] <= 13.5) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 10; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    if (features[16] <= 6.0) {
                                        classes[0] = 4; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 4; 
                                    }
                                }
                            } else {
                                if (features[6] <= 3.5) {
                                    if (features[27] <= 1.0) {
                                        classes[0] = 4; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 9; 
                                }
                            }
                        } else {
                            if (features[5] <= 1.5) {
                                if (features[22] <= 9.5) {
                                    classes[0] = 4; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[1] <= 1162.5) {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    } else {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                classes[0] = 21; 
                                classes[1] = 0; 
                            }
                        }
                    }
                }
            }
        } else {
            if (features[2] <= 1.5) {
                if (features[6] <= 3.5) {
                    if (features[14] <= 3485.0) {
                        if (features[6] <= 2.5) {
                            if (features[26] <= 6.0) {
                                classes[0] = 21; 
                                classes[1] = 0; 
                            } else {
                                if (features[21] <= 0.5) {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                } else {
                                    if (features[27] <= 5.5) {
                                        classes[0] = 13; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                }
                            }
                        } else {
                            if (features[23] <= 1.5) {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            } else {
                                if (features[3] <= 11.5) {
                                    if (features[9] <= 2.5) {
                                        if (features[23] <= 3.0) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 9; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    if (features[14] <= 2568.0) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        if (features[21] <= 0.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (features[3] <= 27.5) {
                            if (features[4] <= 4.5) {
                                if (features[26] <= 7.5) {
                                    if (features[20] <= 3.5) {
                                        classes[0] = 53; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[1] <= 1147.5) {
                                            if (features[27] <= 0.5) {
                                                if (features[0] <= 1.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                } else {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                }
                                            } else {
                                                classes[0] = 10; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 3; 
                                        }
                                    }
                                } else {
                                    classes[0] = 61; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        } else {
                            if (features[27] <= 5.0) {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            } else {
                                classes[0] = 4; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    if (features[22] <= 38.0) {
                        classes[0] = 91; 
                        classes[1] = 0; 
                    } else {
                        classes[0] = 0; 
                        classes[1] = 2; 
                    }
                }
            } else {
                if (features[13] <= 1.5) {
                    if (features[9] <= 1.5) {
                        if (features[28] <= 5.0) {
                            classes[0] = 1; 
                            classes[1] = 0; 
                        } else {
                            classes[0] = 0; 
                            classes[1] = 3; 
                        }
                    } else {
                        if (features[4] <= 3.5) {
                            if (features[12] <= 2.5) {
                                if (features[29] <= 29.5) {
                                    if (features[3] <= 16.5) {
                                        classes[0] = 3; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[23] <= 1.5) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    }
                                } else {
                                    classes[0] = 27; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[6] <= 3.5) {
                                    if (features[5] <= 1.5) {
                                        classes[0] = 11; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[8] <= 75.0) {
                                            if (features[5] <= 2.5) {
                                                if (features[24] <= 1.5) {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 7; 
                                                }
                                            } else {
                                                classes[0] = 6; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 4; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 12; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 25; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[14] <= 3859.5) {
                        classes[0] = 0; 
                        classes[1] = 5; 
                    } else {
                        if (features[17] <= 0.5) {
                            if (features[8] <= 66.0) {
                                if (features[9] <= 2.5) {
                                    if (features[25] <= 12.0) {
                                        if (features[6] <= 2.5) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        }
                                    } else {
                                        classes[0] = 3; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 14; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 9; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[14] <= 5592.5) {
                                classes[0] = 4; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 9; 
                            }
                        }
                    }
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_1(double[] features) {
        int[] classes = new int[2];
        
        if (features[22] <= 3.5) {
            if (features[15] <= 3859.5) {
                classes[0] = 0; 
                classes[1] = 6; 
            } else {
                if (features[13] <= 1.5) {
                    if (features[9] <= 2.5) {
                        if (features[5] <= 3.5) {
                            if (features[25] <= 1.5) {
                                if (features[0] <= 0.5) {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 12; 
                                }
                            } else {
                                if (features[28] <= 1.0) {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 1; 
                            classes[1] = 0; 
                        }
                    } else {
                        if (features[18] <= 12.5) {
                            if (features[15] <= 8571.5) {
                                if (features[12] <= 2.5) {
                                    classes[0] = 3; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            } else {
                                classes[0] = 8; 
                                classes[1] = 0; 
                            }
                        } else {
                            classes[0] = 26; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[25] <= 1.5) {
                        if (features[6] <= 2.5) {
                            if (features[18] <= 21.5) {
                                if (features[12] <= 2.5) {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                } else {
                                    classes[0] = 5; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            }
                        } else {
                            if (features[3] <= 1.5) {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 19; 
                            }
                        }
                    } else {
                        if (features[3] <= 2.0) {
                            if (features[14] <= 2122.0) {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            }
                        } else {
                            classes[0] = 8; 
                            classes[1] = 0; 
                        }
                    }
                }
            }
        } else {
            if (features[9] <= 1.5) {
                if (features[16] <= 3.5) {
                    if (features[17] <= 0.5) {
                        if (features[8] <= 54.0) {
                            if (features[0] <= 0.5) {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            }
                        } else {
                            classes[0] = 18; 
                            classes[1] = 0; 
                        }
                    } else {
                        if (features[22] <= 11.0) {
                            if (features[25] <= 6.0) {
                                if (features[22] <= 5.0) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 8; 
                            }
                        } else {
                            classes[0] = 5; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    classes[0] = 0; 
                    classes[1] = 5; 
                }
            } else {
                if (features[22] <= 38.0) {
                    if (features[1] <= 1033.5) {
                        if (features[11] <= 0.5) {
                            classes[0] = 37; 
                            classes[1] = 0; 
                        } else {
                            if (features[14] <= 2142.5) {
                                if (features[18] <= 22.5) {
                                    if (features[8] <= 75.5) {
                                        if (features[29] <= 28.5) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 5; 
                                    }
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[8] <= 58.5) {
                                    if (features[5] <= 4.5) {
                                        if (features[14] <= 13764.0) {
                                            if (features[23] <= 1.5) {
                                                if (features[5] <= 1.5) {
                                                    if (features[1] <= 868.5) {
                                                        classes[0] = 6; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 3; 
                                                    }
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 6; 
                                                }
                                            } else {
                                                if (features[27] <= 10.0) {
                                                    if (features[3] <= 12.0) {
                                                        if (features[4] <= 1.5) {
                                                            if (features[5] <= 1.5) {
                                                                classes[0] = 0; 
                                                                classes[1] = 3; 
                                                            } else {
                                                                if (features[15] <= 19958.5) {
                                                                    classes[0] = 7; 
                                                                    classes[1] = 0; 
                                                                } else {
                                                                    if (features[3] <= 5.0) {
                                                                        classes[0] = 0; 
                                                                        classes[1] = 1; 
                                                                    } else {
                                                                        classes[0] = 1; 
                                                                        classes[1] = 0; 
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            if (features[12] <= 1.5) {
                                                                if (features[0] <= 0.5) {
                                                                    classes[0] = 0; 
                                                                    classes[1] = 1; 
                                                                } else {
                                                                    if (features[13] <= 1.5) {
                                                                        classes[0] = 10; 
                                                                        classes[1] = 0; 
                                                                    } else {
                                                                        if (features[18] <= 17.0) {
                                                                            classes[0] = 1; 
                                                                            classes[1] = 0; 
                                                                        } else {
                                                                            classes[0] = 0; 
                                                                            classes[1] = 1; 
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                classes[0] = 47; 
                                                                classes[1] = 0; 
                                                            }
                                                        }
                                                    } else {
                                                        if (features[17] <= 0.5) {
                                                            if (features[1] <= 128.0) {
                                                                classes[0] = 0; 
                                                                classes[1] = 2; 
                                                            } else {
                                                                if (features[14] <= 5980.5) {
                                                                    classes[0] = 20; 
                                                                    classes[1] = 0; 
                                                                } else {
                                                                    classes[0] = 0; 
                                                                    classes[1] = 1; 
                                                                }
                                                            }
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 5; 
                                                        }
                                                    }
                                                } else {
                                                    if (features[6] <= 3.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 3; 
                                                    } else {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            }
                                        } else {
                                            classes[0] = 23; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        if (features[29] <= 39.5) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        }
                                    }
                                } else {
                                    if (features[13] <= 0.5) {
                                        classes[0] = 54; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[3] <= 25.5) {
                                            if (features[15] <= 14256.0) {
                                                classes[0] = 82; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[26] <= 3.5) {
                                                    if (features[16] <= 4.5) {
                                                        if (features[3] <= 17.5) {
                                                            if (features[27] <= 0.5) {
                                                                if (features[2] <= 1.5) {
                                                                    classes[0] = 7; 
                                                                    classes[1] = 0; 
                                                                } else {
                                                                    classes[0] = 0; 
                                                                    classes[1] = 2; 
                                                                }
                                                            } else {
                                                                classes[0] = 8; 
                                                                classes[1] = 0; 
                                                            }
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        }
                                                    } else {
                                                        if (features[29] <= 32.5) {
                                                            classes[0] = 2; 
                                                            classes[1] = 0; 
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 6; 
                                                        }
                                                    }
                                                } else {
                                                    if (features[8] <= 86.0) {
                                                        classes[0] = 23; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[14] <= 2545.5) {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        } else {
                                                            classes[0] = 12; 
                                                            classes[1] = 0; 
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            if (features[23] <= 2.5) {
                                                if (features[26] <= 3.0) {
                                                    classes[0] = 2; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 7; 
                                                }
                                            } else {
                                                if (features[15] <= 5670.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                } else {
                                                    classes[0] = 9; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (features[16] <= 5.5) {
                            if (features[20] <= 1.5) {
                                if (features[14] <= 4755.0) {
                                    classes[0] = 20; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[14] <= 4946.5) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        if (features[3] <= 4.5) {
                                            if (features[26] <= 5.5) {
                                                if (features[6] <= 3.5) {
                                                    classes[0] = 4; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[8] <= 38.0) {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    }
                                                }
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            }
                                        } else {
                                            classes[0] = 9; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            } else {
                                if (features[1] <= 1477.5) {
                                    classes[0] = 134; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[28] <= 1.0) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 8; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        } else {
                            if (features[26] <= 6.5) {
                                if (features[17] <= 0.5) {
                                    if (features[12] <= 1.5) {
                                        if (features[26] <= 2.5) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        }
                                    } else {
                                        classes[0] = 17; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 4; 
                                }
                            } else {
                                classes[0] = 16; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    classes[0] = 0; 
                    classes[1] = 6; 
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_2(double[] features) {
        int[] classes = new int[2];
        
        if (features[17] <= 0.5) {
            if (features[29] <= 33.5) {
                if (features[13] <= 1.5) {
                    if (features[1] <= 1471.5) {
                        if (features[21] <= 0.5) {
                            if (features[16] <= 0.5) {
                                classes[0] = 5; 
                                classes[1] = 0; 
                            } else {
                                if (features[11] <= 6.5) {
                                    if (features[20] <= 2.5) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            }
                        } else {
                            if (features[14] <= 2652.0) {
                                if (features[22] <= 2.5) {
                                    if (features[6] <= 3.5) {
                                        if (features[3] <= 7.0) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        }
                                    } else {
                                        classes[0] = 4; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 22; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[18] <= 17.5) {
                                    classes[0] = 66; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[26] <= 5.5) {
                                        if (features[5] <= 2.5) {
                                            if (features[26] <= 3.5) {
                                                classes[0] = 5; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[25] <= 7.5) {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                }
                                            }
                                        } else {
                                            classes[0] = 9; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 16; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        }
                    } else {
                        classes[0] = 0; 
                        classes[1] = 3; 
                    }
                } else {
                    if (features[26] <= 0.5) {
                        if (features[15] <= 5812.5) {
                            classes[0] = 3; 
                            classes[1] = 0; 
                        } else {
                            if (features[8] <= 67.0) {
                                classes[0] = 0; 
                                classes[1] = 12; 
                            } else {
                                if (features[0] <= 1.5) {
                                    if (features[18] <= 14.5) {
                                        classes[0] = 0; 
                                        classes[1] = 5; 
                                    } else {
                                        if (features[0] <= 0.5) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    }
                                } else {
                                    if (features[11] <= 7.0) {
                                        classes[0] = 5; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[3] <= 11.0) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (features[27] <= 1.5) {
                            if (features[27] <= 0.5) {
                                if (features[15] <= 25379.5) {
                                    if (features[25] <= 6.0) {
                                        if (features[8] <= 51.5) {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        } else {
                                            classes[0] = 6; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 10; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                }
                            } else {
                                classes[0] = 18; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[4] <= 3.5) {
                                if (features[9] <= 1.5) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    classes[0] = 7; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[11] <= 6.5) {
                                    if (features[14] <= 4182.5) {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 7; 
                                    }
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            }
                        }
                    }
                }
            } else {
                if (features[14] <= 10668.0) {
                    if (features[23] <= 3.5) {
                        if (features[1] <= 369.5) {
                            classes[0] = 38; 
                            classes[1] = 0; 
                        } else {
                            if (features[27] <= 6.5) {
                                if (features[25] <= 3.5) {
                                    classes[0] = 36; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[23] <= 2.5) {
                                        if (features[6] <= 2.5) {
                                            if (features[28] <= 6.5) {
                                                if (features[26] <= 5.5) {
                                                    if (features[22] <= 12.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 3; 
                                                    } else {
                                                        if (features[16] <= 3.5) {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        } else {
                                                            classes[0] = 4; 
                                                            classes[1] = 0; 
                                                        }
                                                    }
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                }
                                            } else {
                                                classes[0] = 12; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 37; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 44; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                if (features[8] <= 60.5) {
                                    if (features[18] <= 19.0) {
                                        if (features[26] <= 0.5) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 8; 
                                        }
                                    } else {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 12; 
                                    classes[1] = 0; 
                                }
                            }
                        }
                    } else {
                        if (features[18] <= 23.5) {
                            if (features[21] <= 1.5) {
                                if (features[14] <= 3557.5) {
                                    if (features[10] <= 1.5) {
                                        if (features[14] <= 3308.5) {
                                            classes[0] = 5; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                } else {
                                    classes[0] = 24; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[12] <= 3.5) {
                                    if (features[26] <= 2.5) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 6; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 2; 
                        }
                    }
                } else {
                    if (features[26] <= 12.5) {
                        classes[0] = 72; 
                        classes[1] = 0; 
                    } else {
                        if (features[26] <= 14.5) {
                            classes[0] = 0; 
                            classes[1] = 1; 
                        } else {
                            classes[0] = 6; 
                            classes[1] = 0; 
                        }
                    }
                }
            }
        } else {
            if (features[15] <= 16567.5) {
                if (features[10] <= 1.5) {
                    if (features[21] <= 0.5) {
                        if (features[16] <= 0.5) {
                            classes[0] = 4; 
                            classes[1] = 0; 
                        } else {
                            if (features[1] <= 555.0) {
                                classes[0] = 0; 
                                classes[1] = 7; 
                            } else {
                                if (features[29] <= 27.0) {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[22] <= 5.5) {
                                        classes[0] = 0; 
                                        classes[1] = 7; 
                                    } else {
                                        if (features[6] <= 1.5) {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (features[22] <= 5.5) {
                            if (features[3] <= 15.0) {
                                classes[0] = 0; 
                                classes[1] = 8; 
                            } else {
                                if (features[14] <= 2654.0) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[8] <= 37.0) {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            } else {
                                classes[0] = 23; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    if (features[13] <= 0.5) {
                        classes[0] = 24; 
                        classes[1] = 0; 
                    } else {
                        if (features[1] <= 739.5) {
                            if (features[14] <= 6214.5) {
                                if (features[3] <= 3.5) {
                                    if (features[15] <= 8030.5) {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                } else {
                                    classes[0] = 12; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[3] <= 2.5) {
                                    classes[0] = 4; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 6; 
                                }
                            }
                        } else {
                            if (features[1] <= 1448.5) {
                                classes[0] = 32; 
                                classes[1] = 0; 
                            } else {
                                if (features[2] <= 1.5) {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                }
                            }
                        }
                    }
                }
            } else {
                if (features[14] <= 4677.5) {
                    if (features[11] <= 4.0) {
                        if (features[9] <= 3.5) {
                            classes[0] = 0; 
                            classes[1] = 14; 
                        } else {
                            classes[0] = 1; 
                            classes[1] = 0; 
                        }
                    } else {
                        if (features[1] <= 1374.0) {
                            if (features[12] <= 3.5) {
                                if (features[14] <= 2046.0) {
                                    classes[0] = 0; 
                                    classes[1] = 6; 
                                } else {
                                    if (features[29] <= 39.5) {
                                        if (features[18] <= 13.5) {
                                            if (features[27] <= 1.5) {
                                                classes[0] = 6; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[14] <= 3314.5) {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                }
                                            }
                                        } else {
                                            if (features[14] <= 2293.0) {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[22] <= 11.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 5; 
                                                } else {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 5; 
                                    }
                                }
                            } else {
                                classes[0] = 5; 
                                classes[1] = 0; 
                            }
                        } else {
                            classes[0] = 6; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[25] <= 9.5) {
                        if (features[14] <= 19262.0) {
                            if (features[12] <= 1.5) {
                                classes[0] = 11; 
                                classes[1] = 0; 
                            } else {
                                if (features[16] <= 3.5) {
                                    if (features[6] <= 2.5) {
                                        if (features[1] <= 916.0) {
                                            classes[0] = 0; 
                                            classes[1] = 3; 
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        if (features[27] <= 0.5) {
                                            if (features[14] <= 6219.0) {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            } else {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 13; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 4; 
                                }
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 3; 
                        }
                    } else {
                        classes[0] = 9; 
                        classes[1] = 0; 
                    }
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_3(double[] features) {
        int[] classes = new int[2];
        
        if (features[11] <= 7.5) {
            if (features[17] <= 0.5) {
                if (features[29] <= 33.5) {
                    if (features[3] <= 28.5) {
                        if (features[13] <= 0.5) {
                            classes[0] = 37; 
                            classes[1] = 0; 
                        } else {
                            if (features[11] <= 4.5) {
                                if (features[22] <= 3.0) {
                                    if (features[18] <= 13.5) {
                                        if (features[15] <= 11693.0) {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        } else {
                                            if (features[26] <= 1.0) {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            } else {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    } else {
                                        if (features[3] <= 11.5) {
                                            if (features[27] <= 0.5) {
                                                if (features[5] <= 4.0) {
                                                    classes[0] = 2; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                }
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        } else {
                                            classes[0] = 3; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    if (features[20] <= 1.5) {
                                        if (features[29] <= 30.5) {
                                            classes[0] = 10; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[16] <= 0.5) {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[18] <= 23.0) {
                                                    classes[0] = 0; 
                                                    classes[1] = 10; 
                                                } else {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        }
                                    } else {
                                        classes[0] = 27; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                if (features[7] <= 0.5) {
                                    if (features[15] <= 24482.5) {
                                        if (features[28] <= 8.5) {
                                            if (features[3] <= 16.0) {
                                                classes[0] = 19; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[5] <= 3.5) {
                                                    if (features[4] <= 3.5) {
                                                        classes[0] = 6; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    }
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                }
                                            }
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 4; 
                                    }
                                } else {
                                    if (features[15] <= 3375.5) {
                                        if (features[24] <= 2.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 49; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        }
                    } else {
                        if (features[11] <= 6.5) {
                            classes[0] = 0; 
                            classes[1] = 3; 
                        } else {
                            if (features[28] <= 1.0) {
                                classes[0] = 1; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        }
                    }
                } else {
                    if (features[13] <= 1.5) {
                        if (features[8] <= 99.0) {
                            if (features[26] <= 1.5) {
                                if (features[6] <= 1.5) {
                                    if (features[29] <= 38.5) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        classes[0] = 7; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 35; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[12] <= 3.5) {
                                    classes[0] = 138; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[16] <= 1.5) {
                                        if (features[8] <= 93.5) {
                                            classes[0] = 18; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    } else {
                                        classes[0] = 31; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        } else {
                            if (features[0] <= 0.5) {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        }
                    } else {
                        if (features[14] <= 5375.5) {
                            if (features[18] <= 19.0) {
                                if (features[11] <= 5.0) {
                                    classes[0] = 23; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[4] <= 1.5) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        if (features[1] <= 1349.0) {
                                            if (features[8] <= 87.0) {
                                                classes[0] = 13; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[28] <= 2.5) {
                                                    classes[0] = 4; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                }
                                            }
                                        } else {
                                            if (features[25] <= 4.5) {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            } else {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (features[15] <= 16372.0) {
                                    classes[0] = 4; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                }
                            }
                        } else {
                            if (features[4] <= 2.5) {
                                if (features[22] <= 9.5) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    classes[0] = 14; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 41; 
                                classes[1] = 0; 
                            }
                        }
                    }
                }
            } else {
                if (features[10] <= 1.5) {
                    if (features[2] <= 0.5) {
                        classes[0] = 3; 
                        classes[1] = 0; 
                    } else {
                        if (features[28] <= 5.5) {
                            if (features[21] <= 1.5) {
                                if (features[11] <= 4.0) {
                                    if (features[15] <= 16591.5) {
                                        if (features[13] <= 0.5) {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        } else {
                                            if (features[20] <= 3.5) {
                                                classes[0] = 5; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 11; 
                                    }
                                } else {
                                    if (features[14] <= 2324.5) {
                                        if (features[14] <= 2129.5) {
                                            if (features[14] <= 1539.5) {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            } else {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        }
                                    } else {
                                        if (features[12] <= 1.5) {
                                            if (features[3] <= 10.0) {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            }
                                        } else {
                                            if (features[16] <= 3.0) {
                                                classes[0] = 19; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[21] <= 0.5) {
                                                    if (features[5] <= 2.0) {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 2; 
                                                    }
                                                } else {
                                                    classes[0] = 5; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                classes[0] = 6; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[9] <= 3.5) {
                                if (features[26] <= 8.0) {
                                    classes[0] = 0; 
                                    classes[1] = 11; 
                                } else {
                                    if (features[27] <= 6.0) {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    }
                                }
                            } else {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    if (features[21] <= 1.5) {
                        if (features[18] <= 19.5) {
                            if (features[24] <= 2.5) {
                                if (features[2] <= 1.5) {
                                    if (features[28] <= 8.5) {
                                        classes[0] = 10; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[22] <= 10.5) {
                                            classes[0] = 0; 
                                            classes[1] = 3; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    if (features[5] <= 3.5) {
                                        classes[0] = 0; 
                                        classes[1] = 9; 
                                    } else {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                if (features[22] <= 4.5) {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                } else {
                                    if (features[25] <= 0.5) {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    } else {
                                        if (features[22] <= 37.0) {
                                            if (features[10] <= 4.5) {
                                                if (features[6] <= 2.5) {
                                                    if (features[14] <= 13400.5) {
                                                        if (features[22] <= 8.0) {
                                                            if (features[2] <= 1.5) {
                                                                classes[0] = 1; 
                                                                classes[1] = 0; 
                                                            } else {
                                                                classes[0] = 0; 
                                                                classes[1] = 1; 
                                                            }
                                                        } else {
                                                            classes[0] = 8; 
                                                            classes[1] = 0; 
                                                        }
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    }
                                                } else {
                                                    classes[0] = 40; 
                                                    classes[1] = 0; 
                                                }
                                            } else {
                                                if (features[22] <= 25.0) {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                } else {
                                                    classes[0] = 3; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        }
                                    }
                                }
                            }
                        } else {
                            classes[0] = 9; 
                            classes[1] = 0; 
                        }
                    } else {
                        classes[0] = 28; 
                        classes[1] = 0; 
                    }
                }
            }
        } else {
            if (features[26] <= 3.5) {
                if (features[12] <= 1.5) {
                    classes[0] = 0; 
                    classes[1] = 7; 
                } else {
                    if (features[15] <= 17178.5) {
                        if (features[22] <= 2.5) {
                            if (features[4] <= 3.5) {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        } else {
                            if (features[3] <= 7.0) {
                                classes[0] = 0; 
                                classes[1] = 16; 
                            } else {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[18] <= 20.5) {
                            if (features[3] <= 21.0) {
                                classes[0] = 7; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 2; 
                        }
                    }
                }
            } else {
                classes[0] = 5; 
                classes[1] = 0; 
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_4(double[] features) {
        int[] classes = new int[2];
        
        if (features[28] <= 1.5) {
            if (features[11] <= 2.5) {
                if (features[1] <= 1327.0) {
                    if (features[14] <= 3304.5) {
                        if (features[21] <= 1.5) {
                            if (features[24] <= 2.5) {
                                classes[0] = 0; 
                                classes[1] = 11; 
                            } else {
                                if (features[25] <= 0.5) {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                } else {
                                    if (features[13] <= 1.5) {
                                        if (features[15] <= 7894.5) {
                                            if (features[9] <= 2.5) {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            } else {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 6; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    }
                                }
                            }
                        } else {
                            classes[0] = 6; 
                            classes[1] = 0; 
                        }
                    } else {
                        if (features[5] <= 2.0) {
                            if (features[10] <= 2.5) {
                                classes[0] = 5; 
                                classes[1] = 0; 
                            } else {
                                if (features[25] <= 4.5) {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                } else {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 13; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[16] <= 2.0) {
                        classes[0] = 0; 
                        classes[1] = 10; 
                    } else {
                        classes[0] = 2; 
                        classes[1] = 0; 
                    }
                }
            } else {
                if (features[14] <= 2154.5) {
                    if (features[22] <= 0.5) {
                        if (features[2] <= 1.5) {
                            classes[0] = 4; 
                            classes[1] = 0; 
                        } else {
                            classes[0] = 0; 
                            classes[1] = 1; 
                        }
                    } else {
                        if (features[29] <= 28.5) {
                            classes[0] = 1; 
                            classes[1] = 0; 
                        } else {
                            classes[0] = 0; 
                            classes[1] = 8; 
                        }
                    }
                } else {
                    if (features[13] <= 1.5) {
                        if (features[23] <= 0.5) {
                            if (features[9] <= 2.5) {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            } else {
                                classes[0] = 7; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[8] <= 61.5) {
                                if (features[22] <= 2.0) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    if (features[6] <= 1.5) {
                                        if (features[1] <= 639.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 15; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                classes[0] = 37; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[27] <= 0.5) {
                            if (features[8] <= 41.5) {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            } else {
                                if (features[5] <= 2.5) {
                                    if (features[2] <= 1.5) {
                                        classes[0] = 8; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[28] <= 0.5) {
                                            classes[0] = 0; 
                                            classes[1] = 3; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    if (features[0] <= 1.5) {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 4; 
                                    }
                                }
                            }
                        } else {
                            if (features[24] <= 2.5) {
                                if (features[18] <= 19.5) {
                                    if (features[23] <= 2.5) {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                } else {
                                    classes[0] = 6; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 5; 
                                classes[1] = 0; 
                            }
                        }
                    }
                }
            }
        } else {
            if (features[9] <= 1.5) {
                if (features[21] <= 0.5) {
                    if (features[14] <= 4111.5) {
                        classes[0] = 0; 
                        classes[1] = 5; 
                    } else {
                        if (features[11] <= 3.5) {
                            classes[0] = 0; 
                            classes[1] = 3; 
                        } else {
                            if (features[4] <= 3.5) {
                                if (features[1] <= 1349.0) {
                                    classes[0] = 4; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            }
                        }
                    }
                } else {
                    classes[0] = 18; 
                    classes[1] = 0; 
                }
            } else {
                if (features[10] <= 3.5) {
                    if (features[22] <= 23.5) {
                        if (features[15] <= 26424.5) {
                            if (features[21] <= 0.5) {
                                if (features[12] <= 1.5) {
                                    if (features[11] <= 7.5) {
                                        if (features[14] <= 4332.5) {
                                            if (features[22] <= 6.5) {
                                                if (features[8] <= 90.0) {
                                                    classes[0] = 5; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[25] <= 4.0) {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    }
                                                }
                                            } else {
                                                if (features[26] <= 3.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 6; 
                                                } else {
                                                    if (features[15] <= 13793.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 3; 
                                                    } else {
                                                        classes[0] = 3; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            }
                                        } else {
                                            if (features[22] <= 9.5) {
                                                if (features[11] <= 6.5) {
                                                    classes[0] = 6; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[1] <= 367.0) {
                                                        classes[0] = 3; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[15] <= 2658.5) {
                                                            classes[0] = 1; 
                                                            classes[1] = 0; 
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 3; 
                                                        }
                                                    }
                                                }
                                            } else {
                                                classes[0] = 16; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    }
                                } else {
                                    if (features[23] <= 2.5) {
                                        if (features[8] <= 44.5) {
                                            classes[0] = 14; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[10] <= 2.5) {
                                                if (features[3] <= 1.5) {
                                                    if (features[22] <= 11.0) {
                                                        if (features[14] <= 2983.0) {
                                                            classes[0] = 0; 
                                                            classes[1] = 4; 
                                                        } else {
                                                            classes[0] = 1; 
                                                            classes[1] = 0; 
                                                        }
                                                    } else {
                                                        classes[0] = 3; 
                                                        classes[1] = 0; 
                                                    }
                                                } else {
                                                    if (features[27] <= 1.5) {
                                                        classes[0] = 20; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[8] <= 52.5) {
                                                            if (features[9] <= 2.5) {
                                                                classes[0] = 0; 
                                                                classes[1] = 3; 
                                                            } else {
                                                                if (features[10] <= 1.5) {
                                                                    classes[0] = 0; 
                                                                    classes[1] = 1; 
                                                                } else {
                                                                    classes[0] = 6; 
                                                                    classes[1] = 0; 
                                                                }
                                                            }
                                                        } else {
                                                            classes[0] = 11; 
                                                            classes[1] = 0; 
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (features[3] <= 5.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 5; 
                                                } else {
                                                    if (features[13] <= 1.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 2; 
                                                    } else {
                                                        classes[0] = 4; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (features[22] <= 2.5) {
                                            if (features[24] <= 2.5) {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            } else {
                                                classes[0] = 3; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            if (features[22] <= 11.0) {
                                                classes[0] = 51; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[27] <= 2.5) {
                                                    classes[0] = 13; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[12] <= 2.5) {
                                                        classes[0] = 6; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[5] <= 2.0) {
                                                            classes[0] = 1; 
                                                            classes[1] = 0; 
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 2; 
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (features[5] <= 4.5) {
                                    if (features[26] <= 7.5) {
                                        if (features[9] <= 3.5) {
                                            if (features[15] <= 20697.5) {
                                                if (features[6] <= 1.5) {
                                                    if (features[29] <= 46.0) {
                                                        if (features[29] <= 26.5) {
                                                            if (features[18] <= 16.5) {
                                                                classes[0] = 0; 
                                                                classes[1] = 2; 
                                                            } else {
                                                                classes[0] = 2; 
                                                                classes[1] = 0; 
                                                            }
                                                        } else {
                                                            classes[0] = 17; 
                                                            classes[1] = 0; 
                                                        }
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 3; 
                                                    }
                                                } else {
                                                    if (features[26] <= 2.5) {
                                                        if (features[14] <= 2023.0) {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        } else {
                                                            if (features[16] <= 1.5) {
                                                                if (features[8] <= 35.0) {
                                                                    if (features[14] <= 4236.5) {
                                                                        classes[0] = 0; 
                                                                        classes[1] = 1; 
                                                                    } else {
                                                                        classes[0] = 1; 
                                                                        classes[1] = 0; 
                                                                    }
                                                                } else {
                                                                    if (features[0] <= 1.5) {
                                                                        if (features[23] <= 4.0) {
                                                                            classes[0] = 9; 
                                                                            classes[1] = 0; 
                                                                        } else {
                                                                            if (features[14] <= 2699.5) {
                                                                                classes[0] = 0; 
                                                                                classes[1] = 1; 
                                                                            } else {
                                                                                classes[0] = 1; 
                                                                                classes[1] = 0; 
                                                                            }
                                                                        }
                                                                    } else {
                                                                        classes[0] = 17; 
                                                                        classes[1] = 0; 
                                                                    }
                                                                }
                                                            } else {
                                                                classes[0] = 37; 
                                                                classes[1] = 0; 
                                                            }
                                                        }
                                                    } else {
                                                        classes[0] = 54; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            } else {
                                                if (features[22] <= 11.0) {
                                                    if (features[21] <= 2.5) {
                                                        if (features[14] <= 5164.5) {
                                                            if (features[23] <= 0.5) {
                                                                classes[0] = 0; 
                                                                classes[1] = 1; 
                                                            } else {
                                                                if (features[4] <= 2.5) {
                                                                    classes[0] = 0; 
                                                                    classes[1] = 1; 
                                                                } else {
                                                                    classes[0] = 2; 
                                                                    classes[1] = 0; 
                                                                }
                                                            }
                                                        } else {
                                                            classes[0] = 7; 
                                                            classes[1] = 0; 
                                                        }
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 3; 
                                                    }
                                                } else {
                                                    classes[0] = 8; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        } else {
                                            classes[0] = 30; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 55; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    if (features[11] <= 6.5) {
                                        if (features[8] <= 40.0) {
                                            if (features[28] <= 4.0) {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        } else {
                                            classes[0] = 12; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    }
                                }
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 3; 
                        }
                    } else {
                        if (features[15] <= 17797.5) {
                            if (features[7] <= 0.5) {
                                if (features[1] <= 783.5) {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                } else {
                                    classes[0] = 4; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            }
                        } else {
                            classes[0] = 6; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[13] <= 1.5) {
                        classes[0] = 74; 
                        classes[1] = 0; 
                    } else {
                        if (features[15] <= 25085.0) {
                            if (features[14] <= 13710.5) {
                                if (features[6] <= 1.5) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 14; 
                                classes[1] = 0; 
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 1; 
                        }
                    }
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_5(double[] features) {
        int[] classes = new int[2];
        
        if (features[21] <= 0.5) {
            if (features[10] <= 1.5) {
                if (features[2] <= 1.5) {
                    if (features[25] <= 2.5) {
                        if (features[8] <= 71.5) {
                            if (features[15] <= 25317.0) {
                                if (features[24] <= 2.5) {
                                    classes[0] = 0; 
                                    classes[1] = 9; 
                                } else {
                                    if (features[0] <= 1.5) {
                                        classes[0] = 0; 
                                        classes[1] = 5; 
                                    } else {
                                        if (features[1] <= 247.5) {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        } else {
                                            if (features[4] <= 3.5) {
                                                classes[0] = 4; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            }
                                        }
                                    }
                                }
                            } else {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[4] <= 1.5) {
                                if (features[14] <= 1693.5) {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                }
                            } else {
                                if (features[17] <= 0.5) {
                                    classes[0] = 16; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                }
                            }
                        }
                    } else {
                        if (features[1] <= 1170.5) {
                            if (features[27] <= 8.0) {
                                if (features[17] <= 0.5) {
                                    if (features[18] <= 11.5) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        if (features[12] <= 1.5) {
                                            if (features[1] <= 309.0) {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[1] <= 728.0) {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                } else {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        } else {
                                            if (features[25] <= 5.5) {
                                                classes[0] = 14; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[1] <= 980.0) {
                                                    classes[0] = 7; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[3] <= 1.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    } else {
                                                        classes[0] = 3; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (features[16] <= 0.5) {
                                        classes[0] = 4; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[28] <= 4.5) {
                                            if (features[1] <= 734.0) {
                                                classes[0] = 3; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        }
                                    }
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            }
                        } else {
                            classes[0] = 15; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[1] <= 1376.0) {
                        if (features[15] <= 17276.0) {
                            classes[0] = 0; 
                            classes[1] = 12; 
                        } else {
                            if (features[20] <= 2.5) {
                                classes[0] = 1; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            }
                        }
                    } else {
                        if (features[22] <= 3.5) {
                            classes[0] = 2; 
                            classes[1] = 0; 
                        } else {
                            classes[0] = 0; 
                            classes[1] = 2; 
                        }
                    }
                }
            } else {
                if (features[7] <= 0.5) {
                    if (features[28] <= 0.5) {
                        if (features[20] <= 1.5) {
                            classes[0] = 0; 
                            classes[1] = 3; 
                        } else {
                            if (features[24] <= 2.5) {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            } else {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[11] <= 3.5) {
                            classes[0] = 23; 
                            classes[1] = 0; 
                        } else {
                            if (features[6] <= 3.5) {
                                if (features[3] <= 25.5) {
                                    if (features[16] <= 6.5) {
                                        if (features[16] <= 0.5) {
                                            if (features[6] <= 1.5) {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            } else {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 20; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        if (features[23] <= 1.5) {
                                            if (features[29] <= 36.5) {
                                                classes[0] = 4; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        } else {
                                            classes[0] = 4; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    if (features[25] <= 12.0) {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    } else {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                classes[0] = 16; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    if (features[17] <= 0.5) {
                        if (features[0] <= 1.5) {
                            if (features[23] <= 2.5) {
                                if (features[15] <= 21817.5) {
                                    classes[0] = 6; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[26] <= 6.0) {
                                        classes[0] = 0; 
                                        classes[1] = 7; 
                                    } else {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                classes[0] = 15; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[22] <= 17.5) {
                                if (features[14] <= 9129.0) {
                                    if (features[8] <= 38.5) {
                                        if (features[14] <= 5729.0) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 27; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    if (features[20] <= 2.0) {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[2] <= 1.5) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 5; 
                                        }
                                    }
                                }
                            } else {
                                classes[0] = 28; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[1] <= 1291.0) {
                            if (features[11] <= 5.5) {
                                if (features[8] <= 69.0) {
                                    if (features[23] <= 3.5) {
                                        if (features[16] <= 0.5) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 3; 
                                        }
                                    } else {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 5; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 12; 
                            }
                        } else {
                            classes[0] = 6; 
                            classes[1] = 0; 
                        }
                    }
                }
            }
        } else {
            if (features[17] <= 0.5) {
                if (features[3] <= 20.5) {
                    if (features[28] <= 7.5) {
                        if (features[6] <= 2.5) {
                            if (features[16] <= 2.5) {
                                if (features[26] <= 1.0) {
                                    if (features[12] <= 1.5) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 4; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 29; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[4] <= 3.5) {
                                    if (features[27] <= 2.5) {
                                        if (features[10] <= 1.5) {
                                            if (features[8] <= 80.0) {
                                                classes[0] = 4; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        } else {
                                            classes[0] = 15; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    }
                                } else {
                                    classes[0] = 15; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[25] <= 8.5) {
                                if (features[11] <= 7.5) {
                                    classes[0] = 114; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[24] <= 3.5) {
                                        classes[0] = 3; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                }
                            } else {
                                if (features[14] <= 7689.5) {
                                    if (features[1] <= 1053.5) {
                                        classes[0] = 6; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    }
                                } else {
                                    classes[0] = 11; 
                                    classes[1] = 0; 
                                }
                            }
                        }
                    } else {
                        classes[0] = 61; 
                        classes[1] = 0; 
                    }
                } else {
                    if (features[25] <= 10.5) {
                        if (features[6] <= 2.5) {
                            if (features[22] <= 5.5) {
                                if (features[14] <= 2374.5) {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                }
                            } else {
                                if (features[14] <= 6955.5) {
                                    classes[0] = 14; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[20] <= 3.5) {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    }
                                }
                            }
                        } else {
                            classes[0] = 25; 
                            classes[1] = 0; 
                        }
                    } else {
                        classes[0] = 0; 
                        classes[1] = 3; 
                    }
                }
            } else {
                if (features[29] <= 36.5) {
                    if (features[10] <= 1.5) {
                        if (features[27] <= 6.0) {
                            if (features[26] <= 0.5) {
                                if (features[29] <= 23.5) {
                                    if (features[18] <= 12.0) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 5; 
                                }
                            } else {
                                if (features[16] <= 3.5) {
                                    if (features[6] <= 3.5) {
                                        classes[0] = 7; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[8] <= 47.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 4; 
                                }
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 3; 
                        }
                    } else {
                        if (features[12] <= 2.5) {
                            classes[0] = 16; 
                            classes[1] = 0; 
                        } else {
                            if (features[1] <= 591.5) {
                                if (features[29] <= 29.0) {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                }
                            } else {
                                classes[0] = 8; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    if (features[10] <= 1.5) {
                        if (features[1] <= 676.0) {
                            classes[0] = 6; 
                            classes[1] = 0; 
                        } else {
                            if (features[8] <= 59.5) {
                                if (features[5] <= 2.0) {
                                    classes[0] = 0; 
                                    classes[1] = 4; 
                                } else {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 6; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[25] <= 5.5) {
                            if (features[13] <= 0.5) {
                                classes[0] = 11; 
                                classes[1] = 0; 
                            } else {
                                if (features[24] <= 2.5) {
                                    classes[0] = 6; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            }
                        } else {
                            classes[0] = 44; 
                            classes[1] = 0; 
                        }
                    }
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_6(double[] features) {
        int[] classes = new int[2];
        
        if (features[17] <= 0.5) {
            if (features[29] <= 34.5) {
                if (features[21] <= 0.5) {
                    if (features[25] <= 2.5) {
                        if (features[12] <= 3.5) {
                            if (features[13] <= 1.5) {
                                if (features[4] <= 3.5) {
                                    if (features[0] <= 1.5) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 5; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            } else {
                                if (features[6] <= 3.5) {
                                    if (features[25] <= 1.5) {
                                        classes[0] = 0; 
                                        classes[1] = 9; 
                                    } else {
                                        if (features[15] <= 14601.0) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        }
                                    }
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[9] <= 2.5) {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            } else {
                                classes[0] = 8; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[12] <= 2.5) {
                            if (features[5] <= 2.5) {
                                if (features[27] <= 0.5) {
                                    if (features[4] <= 3.5) {
                                        if (features[16] <= 3.0) {
                                            classes[0] = 4; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 7; 
                                }
                            } else {
                                if (features[15] <= 17150.0) {
                                    if (features[26] <= 2.5) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        classes[0] = 7; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 11; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[25] <= 13.0) {
                                if (features[1] <= 1423.0) {
                                    if (features[23] <= 1.5) {
                                        if (features[5] <= 4.0) {
                                            classes[0] = 3; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        }
                                    } else {
                                        classes[0] = 52; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                }
                            } else {
                                if (features[26] <= 9.5) {
                                    classes[0] = 0; 
                                    classes[1] = 4; 
                                } else {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                }
                            }
                        }
                    }
                } else {
                    if (features[8] <= 37.5) {
                        if (features[2] <= 1.5) {
                            if (features[3] <= 18.5) {
                                classes[0] = 8; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        } else {
                            if (features[8] <= 31.5) {
                                classes[0] = 1; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            }
                        }
                    } else {
                        if (features[8] <= 71.5) {
                            classes[0] = 66; 
                            classes[1] = 0; 
                        } else {
                            if (features[28] <= 2.5) {
                                if (features[15] <= 13615.5) {
                                    if (features[23] <= 4.0) {
                                        classes[0] = 21; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[15] <= 9112.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 3; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    if (features[26] <= 3.0) {
                                        if (features[15] <= 16590.5) {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        } else {
                                            classes[0] = 3; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    }
                                }
                            } else {
                                classes[0] = 22; 
                                classes[1] = 0; 
                            }
                        }
                    }
                }
            } else {
                if (features[11] <= 6.5) {
                    if (features[25] <= 31.5) {
                        if (features[21] <= 0.5) {
                            if (features[1] <= 859.0) {
                                if (features[11] <= 5.5) {
                                    classes[0] = 39; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[3] <= 21.0) {
                                        classes[0] = 11; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[25] <= 12.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            } else {
                                if (features[3] <= 7.5) {
                                    classes[0] = 16; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[14] <= 13742.5) {
                                        if (features[25] <= 3.5) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[23] <= 3.0) {
                                                classes[0] = 0; 
                                                classes[1] = 5; 
                                            } else {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    } else {
                                        classes[0] = 5; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        } else {
                            if (features[5] <= 2.5) {
                                classes[0] = 74; 
                                classes[1] = 0; 
                            } else {
                                if (features[14] <= 9936.0) {
                                    classes[0] = 49; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[29] <= 38.5) {
                                        if (features[29] <= 36.5) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    } else {
                                        if (features[15] <= 3030.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 28; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (features[1] <= 443.5) {
                            classes[0] = 0; 
                            classes[1] = 2; 
                        } else {
                            classes[0] = 3; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[0] <= 1.5) {
                        classes[0] = 29; 
                        classes[1] = 0; 
                    } else {
                        if (features[28] <= 2.5) {
                            classes[0] = 12; 
                            classes[1] = 0; 
                        } else {
                            if (features[27] <= 6.5) {
                                if (features[16] <= 7.5) {
                                    if (features[7] <= 0.5) {
                                        classes[0] = 15; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[21] <= 1.5) {
                                            if (features[3] <= 6.5) {
                                                classes[0] = 6; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[15] <= 9405.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                } else {
                                                    if (features[6] <= 3.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 2; 
                                                    } else {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            }
                                        } else {
                                            classes[0] = 6; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 5; 
                            }
                        }
                    }
                }
            }
        } else {
            if (features[28] <= 1.5) {
                if (features[14] <= 5339.5) {
                    if (features[6] <= 3.5) {
                        if (features[6] <= 1.5) {
                            classes[0] = 0; 
                            classes[1] = 4; 
                        } else {
                            if (features[0] <= 0.5) {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            } else {
                                if (features[14] <= 2923.5) {
                                    if (features[10] <= 1.5) {
                                        if (features[27] <= 4.0) {
                                            if (features[8] <= 86.5) {
                                                if (features[2] <= 0.5) {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 8; 
                                                }
                                            } else {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 5; 
                                    classes[1] = 0; 
                                }
                            }
                        }
                    } else {
                        classes[0] = 0; 
                        classes[1] = 8; 
                    }
                } else {
                    if (features[0] <= 1.0) {
                        if (features[15] <= 21331.5) {
                            classes[0] = 0; 
                            classes[1] = 1; 
                        } else {
                            classes[0] = 3; 
                            classes[1] = 0; 
                        }
                    } else {
                        classes[0] = 8; 
                        classes[1] = 0; 
                    }
                }
            } else {
                if (features[16] <= 3.5) {
                    if (features[12] <= 3.5) {
                        if (features[15] <= 23132.5) {
                            if (features[14] <= 3427.0) {
                                if (features[29] <= 31.5) {
                                    if (features[18] <= 12.5) {
                                        if (features[20] <= 1.5) {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 5; 
                                    }
                                } else {
                                    if (features[15] <= 4685.5) {
                                        if (features[6] <= 2.0) {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 6; 
                                        classes[1] = 0; 
                                    }
                                }
                            } else {
                                if (features[14] <= 8981.0) {
                                    classes[0] = 32; 
                                    classes[1] = 0; 
                                } else {
                                    if (features[20] <= 1.5) {
                                        classes[0] = 0; 
                                        classes[1] = 4; 
                                    } else {
                                        if (features[27] <= 8.0) {
                                            classes[0] = 13; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[24] <= 2.5) {
                                                if (features[11] <= 1.5) {
                                                    classes[0] = 2; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                }
                                            } else {
                                                classes[0] = 4; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (features[22] <= 8.5) {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            } else {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        classes[0] = 33; 
                        classes[1] = 0; 
                    }
                } else {
                    if (features[20] <= 1.5) {
                        classes[0] = 9; 
                        classes[1] = 0; 
                    } else {
                        if (features[8] <= 94.5) {
                            if (features[25] <= 11.5) {
                                if (features[28] <= 2.5) {
                                    if (features[3] <= 25.5) {
                                        if (features[22] <= 13.5) {
                                            if (features[13] <= 1.5) {
                                                classes[0] = 0; 
                                                classes[1] = 7; 
                                            } else {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 4; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    if (features[6] <= 3.5) {
                                        classes[0] = 0; 
                                        classes[1] = 10; 
                                    } else {
                                        if (features[22] <= 17.0) {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        }
                                    }
                                }
                            } else {
                                classes[0] = 7; 
                                classes[1] = 0; 
                            }
                        } else {
                            classes[0] = 6; 
                            classes[1] = 0; 
                        }
                    }
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_7(double[] features) {
        int[] classes = new int[2];
        
        if (features[10] <= 1.5) {
            if (features[29] <= 21.5) {
                if (features[14] <= 2983.5) {
                    if (features[18] <= 14.0) {
                        if (features[4] <= 2.0) {
                            classes[0] = 0; 
                            classes[1] = 4; 
                        } else {
                            if (features[15] <= 16904.5) {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            }
                        }
                    } else {
                        classes[0] = 0; 
                        classes[1] = 12; 
                    }
                } else {
                    classes[0] = 5; 
                    classes[1] = 0; 
                }
            } else {
                if (features[13] <= 1.5) {
                    if (features[8] <= 40.0) {
                        if (features[23] <= 1.5) {
                            if (features[17] <= 0.5) {
                                if (features[4] <= 2.0) {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            }
                        } else {
                            if (features[28] <= 5.5) {
                                if (features[1] <= 894.0) {
                                    if (features[7] <= 0.5) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        if (features[9] <= 2.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 11; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            }
                        }
                    } else {
                        if (features[11] <= 4.0) {
                            if (features[15] <= 19609.5) {
                                if (features[29] <= 31.5) {
                                    if (features[18] <= 13.5) {
                                        if (features[12] <= 3.5) {
                                            if (features[29] <= 26.0) {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            } else {
                                                if (features[14] <= 2903.0) {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                } else {
                                                    classes[0] = 3; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        } else {
                                            classes[0] = 4; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 6; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 27; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[23] <= 2.5) {
                                    classes[0] = 0; 
                                    classes[1] = 4; 
                                } else {
                                    if (features[21] <= 0.5) {
                                        if (features[9] <= 3.5) {
                                            classes[0] = 0; 
                                            classes[1] = 2; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 5; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        } else {
                            if (features[15] <= 20288.5) {
                                if (features[22] <= 1.5) {
                                    if (features[15] <= 9585.0) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 7; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 70; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[20] <= 1.5) {
                                    if (features[15] <= 21879.0) {
                                        classes[0] = 0; 
                                        classes[1] = 3; 
                                    } else {
                                        classes[0] = 2; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    if (features[16] <= 3.5) {
                                        classes[0] = 13; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[22] <= 5.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (features[8] <= 94.0) {
                        if (features[22] <= 12.0) {
                            if (features[17] <= 0.5) {
                                if (features[3] <= 26.5) {
                                    if (features[19] <= 3.5) {
                                        if (features[12] <= 3.5) {
                                            if (features[23] <= 3.5) {
                                                if (features[14] <= 2069.0) {
                                                    classes[0] = 0; 
                                                    classes[1] = 4; 
                                                } else {
                                                    if (features[26] <= 2.5) {
                                                        if (features[1] <= 669.5) {
                                                            if (features[22] <= 5.5) {
                                                                classes[0] = 4; 
                                                                classes[1] = 0; 
                                                            } else {
                                                                classes[0] = 0; 
                                                                classes[1] = 1; 
                                                            }
                                                        } else {
                                                            if (features[1] <= 1178.5) {
                                                                classes[0] = 0; 
                                                                classes[1] = 3; 
                                                            } else {
                                                                classes[0] = 1; 
                                                                classes[1] = 0; 
                                                            }
                                                        }
                                                    } else {
                                                        classes[0] = 5; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            } else {
                                                classes[0] = 10; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 12; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        if (features[6] <= 3.5) {
                                            classes[0] = 0; 
                                            classes[1] = 4; 
                                        } else {
                                            if (features[14] <= 2894.5) {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            } else {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                }
                            } else {
                                if (features[3] <= 12.5) {
                                    if (features[2] <= 1.5) {
                                        if (features[22] <= 9.0) {
                                            if (features[18] <= 21.0) {
                                                if (features[4] <= 3.5) {
                                                    classes[0] = 6; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[5] <= 4.0) {
                                                        classes[0] = 2; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    }
                                                }
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            }
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 6; 
                                        }
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 6; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 7; 
                                }
                            }
                        } else {
                            classes[0] = 3; 
                            classes[1] = 0; 
                        }
                    } else {
                        classes[0] = 11; 
                        classes[1] = 0; 
                    }
                }
            }
        } else {
            if (features[11] <= 6.5) {
                if (features[29] <= 57.5) {
                    if (features[11] <= 3.5) {
                        if (features[5] <= 2.5) {
                            if (features[20] <= 1.5) {
                                if (features[15] <= 20353.5) {
                                    if (features[1] <= 1342.5) {
                                        classes[0] = 9; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 4; 
                                }
                            } else {
                                if (features[14] <= 12676.0) {
                                    if (features[11] <= 1.5) {
                                        classes[0] = 19; 
                                        classes[1] = 0; 
                                    } else {
                                        if (features[8] <= 64.0) {
                                            classes[0] = 12; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[10] <= 2.5) {
                                                if (features[13] <= 1.5) {
                                                    classes[0] = 3; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[3] <= 8.0) {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    } else {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 28; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 92; 
                            classes[1] = 0; 
                        }
                    } else {
                        if (features[18] <= 21.5) {
                            classes[0] = 135; 
                            classes[1] = 0; 
                        } else {
                            if (features[14] <= 4524.5) {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            } else {
                                classes[0] = 6; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    if (features[28] <= 5.0) {
                        classes[0] = 1; 
                        classes[1] = 0; 
                    } else {
                        classes[0] = 0; 
                        classes[1] = 2; 
                    }
                }
            } else {
                if (features[28] <= 8.5) {
                    if (features[22] <= 16.5) {
                        if (features[9] <= 1.5) {
                            if (features[21] <= 2.0) {
                                classes[0] = 0; 
                                classes[1] = 6; 
                            } else {
                                classes[0] = 1; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[18] <= 19.5) {
                                if (features[15] <= 25973.5) {
                                    if (features[17] <= 0.5) {
                                        if (features[26] <= 1.5) {
                                            if (features[26] <= 0.5) {
                                                classes[0] = 7; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[27] <= 1.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 3; 
                                                } else {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        } else {
                                            if (features[7] <= 0.5) {
                                                if (features[6] <= 1.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                } else {
                                                    if (features[26] <= 7.5) {
                                                        classes[0] = 21; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[20] <= 2.0) {
                                                            classes[0] = 0; 
                                                            classes[1] = 2; 
                                                        } else {
                                                            classes[0] = 3; 
                                                            classes[1] = 0; 
                                                        }
                                                    }
                                                }
                                            } else {
                                                classes[0] = 32; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    } else {
                                        if (features[14] <= 4550.5) {
                                            classes[0] = 9; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[24] <= 3.5) {
                                                if (features[29] <= 32.0) {
                                                    classes[0] = 4; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[8] <= 81.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 7; 
                                                    } else {
                                                        classes[0] = 3; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            } else {
                                                classes[0] = 5; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                }
                            } else {
                                classes[0] = 20; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[18] <= 13.5) {
                            if (features[22] <= 18.5) {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            } else {
                                classes[0] = 11; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[17] <= 0.5) {
                                if (features[22] <= 21.5) {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                } else {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 0; 
                                classes[1] = 8; 
                            }
                        }
                    }
                } else {
                    classes[0] = 26; 
                    classes[1] = 0; 
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_8(double[] features) {
        int[] classes = new int[2];
        
        if (features[21] <= 0.5) {
            if (features[10] <= 1.5) {
                if (features[12] <= 1.5) {
                    if (features[1] <= 623.5) {
                        if (features[20] <= 2.5) {
                            classes[0] = 0; 
                            classes[1] = 10; 
                        } else {
                            if (features[24] <= 2.5) {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            } else {
                                classes[0] = 1; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[28] <= 5.5) {
                            if (features[7] <= 0.5) {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            } else {
                                if (features[15] <= 7079.0) {
                                    if (features[17] <= 0.5) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 1; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 1; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[2] <= 1.5) {
                        if (features[17] <= 0.5) {
                            if (features[28] <= 5.5) {
                                if (features[3] <= 28.5) {
                                    if (features[5] <= 3.5) {
                                        if (features[29] <= 18.5) {
                                            if (features[0] <= 1.0) {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            }
                                        } else {
                                            classes[0] = 42; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        if (features[3] <= 8.5) {
                                            classes[0] = 3; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[20] <= 3.0) {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            } else {
                                                if (features[25] <= 1.5) {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            } else {
                                classes[0] = 12; 
                                classes[1] = 0; 
                            }
                        } else {
                            if (features[29] <= 36.5) {
                                if (features[16] <= 2.5) {
                                    if (features[22] <= 1.5) {
                                        if (features[29] <= 19.5) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 6; 
                                        }
                                    } else {
                                        if (features[12] <= 3.5) {
                                            classes[0] = 6; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[19] <= 3.5) {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            } else {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 5; 
                                }
                            } else {
                                classes[0] = 9; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[24] <= 3.5) {
                            classes[0] = 0; 
                            classes[1] = 9; 
                        } else {
                            if (features[9] <= 2.5) {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        }
                    }
                }
            } else {
                if (features[9] <= 1.5) {
                    if (features[3] <= 3.5) {
                        classes[0] = 1; 
                        classes[1] = 0; 
                    } else {
                        classes[0] = 0; 
                        classes[1] = 8; 
                    }
                } else {
                    if (features[11] <= 3.5) {
                        classes[0] = 66; 
                        classes[1] = 0; 
                    } else {
                        if (features[13] <= 0.5) {
                            if (features[15] <= 9135.5) {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            }
                        } else {
                            if (features[15] <= 2400.5) {
                                classes[0] = 9; 
                                classes[1] = 0; 
                            } else {
                                if (features[15] <= 2503.5) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    if (features[9] <= 3.5) {
                                        if (features[27] <= 2.5) {
                                            if (features[26] <= 4.5) {
                                                if (features[15] <= 17060.0) {
                                                    if (features[1] <= 1145.0) {
                                                        classes[0] = 28; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[1] <= 1167.5) {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        } else {
                                                            classes[0] = 2; 
                                                            classes[1] = 0; 
                                                        }
                                                    }
                                                } else {
                                                    if (features[11] <= 6.0) {
                                                        classes[0] = 8; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[15] <= 20051.0) {
                                                            classes[0] = 0; 
                                                            classes[1] = 6; 
                                                        } else {
                                                            if (features[7] <= 0.5) {
                                                                if (features[27] <= 1.5) {
                                                                    classes[0] = 2; 
                                                                    classes[1] = 0; 
                                                                } else {
                                                                    classes[0] = 0; 
                                                                    classes[1] = 1; 
                                                                }
                                                            } else {
                                                                classes[0] = 7; 
                                                                classes[1] = 0; 
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                classes[0] = 32; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            if (features[17] <= 0.5) {
                                                if (features[8] <= 66.5) {
                                                    if (features[22] <= 17.5) {
                                                        if (features[19] <= 3.5) {
                                                            if (features[8] <= 42.5) {
                                                                classes[0] = 6; 
                                                                classes[1] = 0; 
                                                            } else {
                                                                if (features[20] <= 2.5) {
                                                                    classes[0] = 1; 
                                                                    classes[1] = 0; 
                                                                } else {
                                                                    classes[0] = 0; 
                                                                    classes[1] = 2; 
                                                                }
                                                            }
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 2; 
                                                        }
                                                    } else {
                                                        classes[0] = 6; 
                                                        classes[1] = 0; 
                                                    }
                                                } else {
                                                    classes[0] = 14; 
                                                    classes[1] = 0; 
                                                }
                                            } else {
                                                if (features[13] <= 1.5) {
                                                    if (features[29] <= 43.0) {
                                                        classes[0] = 3; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    }
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 5; 
                                                }
                                            }
                                        }
                                    } else {
                                        if (features[28] <= 7.5) {
                                            if (features[23] <= 2.5) {
                                                classes[0] = 0; 
                                                classes[1] = 7; 
                                            } else {
                                                classes[0] = 3; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            classes[0] = 5; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (features[28] <= 0.5) {
                if (features[6] <= 3.5) {
                    if (features[16] <= 1.5) {
                        if (features[6] <= 2.5) {
                            classes[0] = 0; 
                            classes[1] = 2; 
                        } else {
                            if (features[1] <= 1059.0) {
                                classes[0] = 5; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        }
                    } else {
                        if (features[12] <= 1.5) {
                            if (features[8] <= 94.5) {
                                classes[0] = 12; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 2; 
                            }
                        } else {
                            classes[0] = 20; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[15] <= 14691.0) {
                        classes[0] = 0; 
                        classes[1] = 4; 
                    } else {
                        if (features[16] <= 0.5) {
                            classes[0] = 0; 
                            classes[1] = 1; 
                        } else {
                            classes[0] = 7; 
                            classes[1] = 0; 
                        }
                    }
                }
            } else {
                if (features[11] <= 6.5) {
                    if (features[1] <= 132.5) {
                        classes[0] = 0; 
                        classes[1] = 1; 
                    } else {
                        if (features[29] <= 26.5) {
                            if (features[8] <= 35.5) {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            } else {
                                if (features[23] <= 5.5) {
                                    if (features[16] <= 1.5) {
                                        if (features[20] <= 1.5) {
                                            if (features[11] <= 4.0) {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            }
                                        } else {
                                            classes[0] = 8; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        if (features[24] <= 3.5) {
                                            classes[0] = 0; 
                                            classes[1] = 3; 
                                        } else {
                                            classes[0] = 1; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 7; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[23] <= 2.5) {
                                if (features[8] <= 44.5) {
                                    if (features[26] <= 1.0) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        if (features[29] <= 35.0) {
                                            if (features[5] <= 0.5) {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            } else {
                                                if (features[3] <= 9.5) {
                                                    if (features[0] <= 1.5) {
                                                        classes[0] = 3; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[14] <= 3586.0) {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        } else {
                                                            classes[0] = 1; 
                                                            classes[1] = 0; 
                                                        }
                                                    }
                                                } else {
                                                    classes[0] = 6; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        } else {
                                            classes[0] = 18; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 94; 
                                    classes[1] = 0; 
                                }
                            } else {
                                classes[0] = 141; 
                                classes[1] = 0; 
                            }
                        }
                    }
                } else {
                    if (features[12] <= 2.5) {
                        if (features[25] <= 10.5) {
                            classes[0] = 26; 
                            classes[1] = 0; 
                        } else {
                            if (features[24] <= 1.5) {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            } else {
                                classes[0] = 7; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        if (features[21] <= 1.5) {
                            if (features[17] <= 0.5) {
                                if (features[25] <= 3.5) {
                                    if (features[24] <= 3.5) {
                                        classes[0] = 3; 
                                        classes[1] = 0; 
                                    } else {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    }
                                } else {
                                    classes[0] = 26; 
                                    classes[1] = 0; 
                                }
                            } else {
                                if (features[1] <= 564.5) {
                                    classes[0] = 0; 
                                    classes[1] = 3; 
                                } else {
                                    if (features[14] <= 4675.0) {
                                        classes[0] = 0; 
                                        classes[1] = 1; 
                                    } else {
                                        classes[0] = 10; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        } else {
                            if (features[6] <= 3.5) {
                                classes[0] = 0; 
                                classes[1] = 4; 
                            } else {
                                classes[0] = 2; 
                                classes[1] = 0; 
                            }
                        }
                    }
                }
            }
        }
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
    
    public static int predict_9(double[] features) {
        int[] classes = new int[2];
        
        if (features[14] <= 10661.5) {
            if (features[6] <= 1.5) {
                if (features[15] <= 2816.5) {
                    classes[0] = 14; 
                    classes[1] = 0; 
                } else {
                    if (features[12] <= 3.5) {
                        if (features[9] <= 3.5) {
                            if (features[18] <= 16.5) {
                                if (features[11] <= 7.5) {
                                    if (features[15] <= 10736.5) {
                                        if (features[9] <= 2.0) {
                                            classes[0] = 0; 
                                            classes[1] = 3; 
                                        } else {
                                            if (features[13] <= 0.5) {
                                                classes[0] = 1; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[10] <= 1.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 5; 
                                                } else {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                }
                                            }
                                        }
                                    } else {
                                        if (features[16] <= 1.5) {
                                            classes[0] = 14; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[3] <= 3.0) {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            } else {
                                                if (features[14] <= 3306.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                } else {
                                                    if (features[18] <= 12.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    } else {
                                                        if (features[18] <= 14.5) {
                                                            classes[0] = 5; 
                                                            classes[1] = 0; 
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 8; 
                                }
                            } else {
                                if (features[27] <= 3.0) {
                                    if (features[27] <= 0.5) {
                                        classes[0] = 0; 
                                        classes[1] = 14; 
                                    } else {
                                        if (features[15] <= 14468.5) {
                                            classes[0] = 2; 
                                            classes[1] = 0; 
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 7; 
                                        }
                                    }
                                } else {
                                    classes[0] = 2; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 7; 
                            classes[1] = 0; 
                        }
                    } else {
                        if (features[29] <= 45.5) {
                            if (features[17] <= 0.5) {
                                classes[0] = 16; 
                                classes[1] = 0; 
                            } else {
                                if (features[10] <= 1.5) {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                } else {
                                    classes[0] = 1; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[3] <= 6.5) {
                                classes[0] = 4; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            }
                        }
                    }
                }
            } else {
                if (features[2] <= 1.5) {
                    if (features[5] <= 0.5) {
                        if (features[4] <= 3.5) {
                            if (features[14] <= 8169.5) {
                                classes[0] = 3; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 1; 
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 4; 
                        }
                    } else {
                        if (features[22] <= 29.5) {
                            if (features[10] <= 1.5) {
                                if (features[22] <= 1.5) {
                                    if (features[21] <= 1.5) {
                                        if (features[1] <= 1242.5) {
                                            if (features[17] <= 0.5) {
                                                if (features[14] <= 1927.5) {
                                                    if (features[1] <= 338.0) {
                                                        if (features[14] <= 1076.5) {
                                                            classes[0] = 1; 
                                                            classes[1] = 0; 
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 2; 
                                                        }
                                                    } else {
                                                        classes[0] = 6; 
                                                        classes[1] = 0; 
                                                    }
                                                } else {
                                                    classes[0] = 10; 
                                                    classes[1] = 0; 
                                                }
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 8; 
                                            }
                                        } else {
                                            if (features[8] <= 57.5) {
                                                classes[0] = 0; 
                                                classes[1] = 6; 
                                            } else {
                                                classes[0] = 2; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    } else {
                                        classes[0] = 5; 
                                        classes[1] = 0; 
                                    }
                                } else {
                                    if (features[17] <= 0.5) {
                                        if (features[13] <= 1.5) {
                                            classes[0] = 59; 
                                            classes[1] = 0; 
                                        } else {
                                            if (features[1] <= 892.0) {
                                                if (features[15] <= 16245.5) {
                                                    if (features[25] <= 13.5) {
                                                        classes[0] = 17; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    }
                                                } else {
                                                    if (features[25] <= 6.0) {
                                                        classes[0] = 0; 
                                                        classes[1] = 5; 
                                                    } else {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            } else {
                                                classes[0] = 21; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    } else {
                                        if (features[29] <= 36.5) {
                                            if (features[3] <= 6.5) {
                                                if (features[28] <= 4.5) {
                                                    if (features[16] <= 6.0) {
                                                        classes[0] = 12; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[9] <= 2.5) {
                                                            classes[0] = 1; 
                                                            classes[1] = 0; 
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        }
                                                    }
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 2; 
                                                }
                                            } else {
                                                if (features[14] <= 2546.5) {
                                                    classes[0] = 0; 
                                                    classes[1] = 7; 
                                                } else {
                                                    if (features[14] <= 3032.0) {
                                                        classes[0] = 2; 
                                                        classes[1] = 0; 
                                                    } else {
                                                        if (features[13] <= 0.5) {
                                                            classes[0] = 0; 
                                                            classes[1] = 4; 
                                                        } else {
                                                            if (features[25] <= 2.5) {
                                                                classes[0] = 0; 
                                                                classes[1] = 1; 
                                                            } else {
                                                                classes[0] = 1; 
                                                                classes[1] = 0; 
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            classes[0] = 20; 
                                            classes[1] = 0; 
                                        }
                                    }
                                }
                            } else {
                                if (features[29] <= 23.0) {
                                    classes[0] = 0; 
                                    classes[1] = 1; 
                                } else {
                                    if (features[28] <= 6.5) {
                                        if (features[4] <= 2.5) {
                                            if (features[28] <= 5.0) {
                                                if (features[26] <= 3.5) {
                                                    classes[0] = 14; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[26] <= 4.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    } else {
                                                        classes[0] = 1; 
                                                        classes[1] = 0; 
                                                    }
                                                }
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            }
                                        } else {
                                            classes[0] = 62; 
                                            classes[1] = 0; 
                                        }
                                    } else {
                                        classes[0] = 72; 
                                        classes[1] = 0; 
                                    }
                                }
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 6; 
                        }
                    }
                } else {
                    if (features[13] <= 1.5) {
                        if (features[15] <= 23120.0) {
                            if (features[9] <= 1.5) {
                                classes[0] = 0; 
                                classes[1] = 3; 
                            } else {
                                if (features[8] <= 74.5) {
                                    if (features[14] <= 2196.0) {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    } else {
                                        if (features[21] <= 0.5) {
                                            if (features[27] <= 2.0) {
                                                classes[0] = 0; 
                                                classes[1] = 2; 
                                            } else {
                                                classes[0] = 6; 
                                                classes[1] = 0; 
                                            }
                                        } else {
                                            if (features[1] <= 419.0) {
                                                if (features[10] <= 2.5) {
                                                    classes[0] = 10; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 1; 
                                                }
                                            } else {
                                                classes[0] = 35; 
                                                classes[1] = 0; 
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 34; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            if (features[8] <= 40.0) {
                                classes[0] = 0; 
                                classes[1] = 5; 
                            } else {
                                if (features[16] <= 5.5) {
                                    if (features[15] <= 23837.5) {
                                        classes[0] = 0; 
                                        classes[1] = 2; 
                                    } else {
                                        if (features[22] <= 4.5) {
                                            classes[0] = 0; 
                                            classes[1] = 1; 
                                        } else {
                                            classes[0] = 12; 
                                            classes[1] = 0; 
                                        }
                                    }
                                } else {
                                    classes[0] = 0; 
                                    classes[1] = 2; 
                                }
                            }
                        }
                    } else {
                        if (features[9] <= 3.5) {
                            if (features[14] <= 2333.5) {
                                classes[0] = 0; 
                                classes[1] = 7; 
                            } else {
                                if (features[27] <= 7.5) {
                                    if (features[12] <= 2.5) {
                                        if (features[3] <= 7.5) {
                                            if (features[14] <= 6800.5) {
                                                classes[0] = 5; 
                                                classes[1] = 0; 
                                            } else {
                                                if (features[17] <= 0.5) {
                                                    classes[0] = 1; 
                                                    classes[1] = 0; 
                                                } else {
                                                    classes[0] = 0; 
                                                    classes[1] = 4; 
                                                }
                                            }
                                        } else {
                                            classes[0] = 0; 
                                            classes[1] = 6; 
                                        }
                                    } else {
                                        if (features[7] <= 0.5) {
                                            if (features[16] <= 7.5) {
                                                classes[0] = 14; 
                                                classes[1] = 0; 
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 1; 
                                            }
                                        } else {
                                            if (features[27] <= 2.5) {
                                                if (features[20] <= 3.5) {
                                                    classes[0] = 7; 
                                                    classes[1] = 0; 
                                                } else {
                                                    if (features[8] <= 50.5) {
                                                        classes[0] = 0; 
                                                        classes[1] = 1; 
                                                    } else {
                                                        if (features[24] <= 2.5) {
                                                            classes[0] = 1; 
                                                            classes[1] = 0; 
                                                        } else {
                                                            classes[0] = 0; 
                                                            classes[1] = 1; 
                                                        }
                                                    }
                                                }
                                            } else {
                                                classes[0] = 0; 
                                                classes[1] = 3; 
                                            }
                                        }
                                    }
                                } else {
                                    classes[0] = 6; 
                                    classes[1] = 0; 
                                }
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 4; 
                        }
                    }
                }
            }
        } else {
            if (features[1] <= 294.0) {
                if (features[15] <= 25676.5) {
                    if (features[6] <= 1.5) {
                        classes[0] = 0; 
                        classes[1] = 2; 
                    } else {
                        classes[0] = 17; 
                        classes[1] = 0; 
                    }
                } else {
                    classes[0] = 0; 
                    classes[1] = 3; 
                }
            } else {
                if (features[14] <= 19743.0) {
                    if (features[28] <= 8.5) {
                        classes[0] = 87; 
                        classes[1] = 0; 
                    } else {
                        if (features[25] <= 10.5) {
                            classes[0] = 0; 
                            classes[1] = 1; 
                        } else {
                            classes[0] = 29; 
                            classes[1] = 0; 
                        }
                    }
                } else {
                    if (features[27] <= 2.5) {
                        classes[0] = 0; 
                        classes[1] = 1; 
                    } else {
                        classes[0] = 1; 
                        classes[1] = 0; 
                    }
                }
            }
        }

        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 2; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;

    }
    
    public static int predict(double[] features) {
        int n_classes = 2;
        int[] classes = new int[n_classes];
        classes[RandomForestClassifier.predict_0(features)]++;
        classes[RandomForestClassifier.predict_1(features)]++;
        classes[RandomForestClassifier.predict_2(features)]++;
        classes[RandomForestClassifier.predict_3(features)]++;
        classes[RandomForestClassifier.predict_4(features)]++;
        classes[RandomForestClassifier.predict_5(features)]++;
        classes[RandomForestClassifier.predict_6(features)]++;
        classes[RandomForestClassifier.predict_7(features)]++;
        classes[RandomForestClassifier.predict_8(features)]++;
        classes[RandomForestClassifier.predict_9(features)]++;
    
        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < n_classes; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }
}