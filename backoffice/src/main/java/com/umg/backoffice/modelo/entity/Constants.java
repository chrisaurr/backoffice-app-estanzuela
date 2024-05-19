package com.umg.backoffice.modelo.entity;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class Constants {

    public static Integer ESTADO_ELIMINADO = 0;
    public static Integer ESTADO_SIN_PROCESO = 1;
    public static Integer ESTADO_SOLUCIONADO = 2;
    public static Integer ESTADO_NO_APLICA = 3;

    public static Integer ESTADO_ACTIVO = 1;

    public static Integer ASIGNACION_TERMINADA = 0;
    public static Integer ASIGNACION_ACTIVA = 1;

}
