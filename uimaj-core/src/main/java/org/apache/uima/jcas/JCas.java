/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
 
package org.apache.uima.jcas;

import java.io.InputStream;

import org.apache.uima.cas.AbstractCas;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CasOwner;
import org.apache.uima.cas.ConstraintFactory;
import org.apache.uima.cas.FSIndexRepository;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeaturePath;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.SofaID;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.LowLevelCAS;
import org.apache.uima.cas.impl.LowLevelIndexRepository;
import org.apache.uima.cas.text.TCASRuntimeException;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.FloatArray;
import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.Sofa;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.DocumentAnnotation;

/**
 * Java Cover Classes based Object-oriented CAS (Common Analysis System) API.
 * 
 * <p>
 * A <code>JCas</code> object provides the starting point for working with the CAS using
 * Java Cover Classes for each type, generated by the utility JCasGen. 
 * <p>
 * This interface extends the CAS Interface, providing all the same functionality, plus
 * some specific to the JCas.
 * <p>
 * It supports the creation of new instances of CAS types, using the normal Java "new"
 * operator.  
 * <p>
 * You can create a <code>JCas</code> object from a CAS object by calling the getJCas()
 * method on the CAS object. 
 */

public interface JCas extends AbstractCas {

  /**
   * (internal use)
   */
  public final static int INVALID_FEATURE_CODE = -1;

  // *********************************
  // * Getters for read-only objects *
  // *********************************
  /** return the FSIndexRepository object for this Cas. */
  public abstract FSIndexRepository getFSIndexRepository();

  public abstract LowLevelIndexRepository getLowLevelIndexRepository();

  /** return the Cas object for this JCas instantiation */
  public abstract CAS getCas();

  /** internal use */
  public abstract CASImpl getCasImpl();

  /** internal use */
  public abstract LowLevelCAS getLowLevelCas();

  /** return the Cas TypeSystem for this JCas. */
  public abstract TypeSystem getTypeSystem();

  /**
   * get the JCas _Type instance for a particular CAS type constant
   * 
   * @param i
   *          the CAS type constant, written as Foo.type
   * @return the instance of the JCas xxx_Type object for the specified type
   */
  public abstract TOP_Type getType(int i);

  /**
   * Given Foo.type, return the corresponding CAS Type object. This is useful in the methods which
   * require a CAS Type, for instance iterator creation.
   * 
   * @param i -
   *          index returned by Foo.type
   * @return the CAS Java Type object for this CAS Type.
   */
  public abstract Type getCasType(int i);

  /**
   * get the JCas x_Type instance for a particular Java instance of a type
   * 
   * @param instance
   * @return the associated xxx_Type instance
   * @deprecated use instance.jcasType instead - faster
   */
  public abstract TOP_Type getType(TOP instance);

  /**
   * Internal use - looks up a type-name-string in the CAS type system and returns the Cas Type
   * object. Throws CASException if the type isn't found
   */
  public abstract Type getRequiredType(String s) throws CASException;

  /**
   * Internal use - look up a feature-name-string in the CAS type system and returns the Cas Feature
   * object. Throws CASException if the feature isn't found
   */
  public abstract Feature getRequiredFeature(Type t, String s) throws CASException;

  /**
   * Internal Use - look up a feature-name-string in the CAS type system and returns the Cas Feature
   * object. If the feature isn't found, adds an exception to the errorSet but doesn't throw
   */

  public abstract Feature getRequiredFeatureDE(Type t, String s, String rangeName, boolean featOkTst);

  /**
   * internal - sets the corresponding Java instance for a Cas instance
   */
  public abstract void putJfsFromCaddr(int casAddr, FeatureStructure fs);

  /**
   * internal - sets the corresponding Java instance for a Cas instance
   */
  public abstract TOP getJfsFromCaddr(int casAddr);

  /** calls the cas reset() function */
  public abstract void reset();

  public abstract void checkArrayBounds(int fsRef, int pos);

  /**
   * @deprecated As of v2.0, use {#getView(String)}. From the view you can access the Sofa data, or
   *             call {@link #getSofa()} if you truly need to access the SofaFS object.
   */
  public abstract Sofa getSofa(SofaID sofaID);

  public abstract Sofa getSofa();

  public abstract JCas createView(String sofaID) throws CASException;

  public abstract JCas getJCas(Sofa sofa) throws CASException;

  public abstract FSIterator getSofaIterator();

  public abstract JFSIndexRepository getJFSIndexRepository();

  /**
   * @see org.apache.uima.cas.text.TCAS#getDocumentAnnotation
   * 
   * @return The one instance of the DocumentAnnotation annotation
   * 
   * @deprecated Use of this method is not safe and may cause ClassCastExceptions in certain
   *             deployments. Instead, use {@link #getDocumentAnnotationFs()}, which has a return
   *             type of TOP, and typecast the returned object to
   *             {@link org.apache.uima.jcas.tcas.DocumentAnnotation}.
   */
  public abstract DocumentAnnotation getDocumentAnnotation();

  /**
   * Gets the document annotation. The object returned from this method can be typecast to
   * {@link org.apache.uima.jcas.tcas.DocumentAnnotation}.
   * <p>
   * The reason that the return type of this method is not DocumentAnnotation is because of problems
   * that arise when using the UIMA Extension ClassLoader to load annotator classes. The
   * DocumentAnnotation type may be defined in the UIMA extension ClassLoader, differently than in
   * the framework ClassLoader.
   * 
   * @return The one instance of the DocumentAnnotation annotation.
   * @see org.apache.uima.cas.text.TCAS#getDocumentAnnotation
   */
  public abstract TOP getDocumentAnnotationFs();

  /**
   * @see org.apache.uima.cas.text.TCAS#getDocumentText
   * 
   * @return String representing the Sofa data
   */
  public abstract String getDocumentText();

  /**
   * @see org.apache.uima.cas.text.TCAS#getSofaDataArray
   * 
   * @return FSARRAY representing the Sofa data
   */
  public abstract FSArray getSofaDataArray();

  /**
   * @see org.apache.uima.cas.text.TCAS#getSofaDataURI
   * 
   * @return String holding the URI of the Sofa data
   */
  public abstract String getSofaDataURI();

  /**
   * @see org.apache.uima.cas.text.TCAS#setDocumentText
   * 
   */
  public abstract void setDocumentText(String text) throws TCASRuntimeException;

  /**
   * @see org.apache.uima.cas.text.TCAS#setSofaDataString
   * 
   */
  public abstract void setSofaDataString(String text, String mime) throws TCASRuntimeException;

  /**
   * @see org.apache.uima.cas.text.TCAS#setSofaDataArray
   * 
   */
  public abstract void setSofaDataArray(TOP array, String mime) throws TCASRuntimeException;

  /**
   * @see org.apache.uima.cas.text.TCAS#setSofaDataURI
   * 
   */
  public abstract void setSofaDataURI(String uri, String mime) throws TCASRuntimeException;

  /**
   * @see org.apache.uima.cas.text.TCAS#getDocumentLanguage
   * 
   * @return String representing the language of the document in the JCAS
   */
  public abstract String getDocumentLanguage();

  /**
   * @see org.apache.uima.cas.text.TCAS#setDocumentLanguage
   * 
   */
  public abstract void setDocumentLanguage(String language) throws TCASRuntimeException;

  /**
   * @see org.apache.uima.cas.text.TCAS#getSofaDataStream
   * 
   * @return InputStream handle to the Sofa data
   */
  public abstract InputStream getSofaDataStream();

  /**
   * @see org.apache.uima.cas.CAS#getConstraintFactory
   * @return ConstraintFactory instance
   */
  public abstract ConstraintFactory getConstraintFactory();

  /**
   * @see org.apache.uima.cas.CAS#createFeaturePath
   * 
   * @return FeaturePath instance
   */
  public abstract FeaturePath createFeaturePath();

  /**
   * @see org.apache.uima.cas.CAS#createFilteredIterator
   * 
   * @param it
   *          the iterator to filter
   * @param constraint
   *          the constraint to apply to the iterator to filter the results
   * @return a filtered iterator
   */
  public abstract FSIterator createFilteredIterator(FSIterator it, FSMatchConstraint constraint);

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */

  public abstract StringArray getStringArray0L();

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */
  public abstract IntegerArray getIntegerArray0L();

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */
  public abstract FloatArray getFloatArray0L();

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */
  public abstract FSArray getFSArray0L();

  /**
   * initialize the JCas for new Cas content. Not used, does nothing.
   * 
   * @deprecated not required, does nothing
   */
  public abstract void processInit();

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.uima.cas.AbstractCas_ImplBase#setOwn
   */
  public abstract void setOwner(CasOwner aCasOwner);

  /**
   * @see org.apache.uima.cas.AbstractCas_ImplBase#release()
   */
  public abstract void release();

  /**
   * @see org.apache.uima.cas.CAS#getView(String localViewName);
   * @param localViewName
   * @return
   * @throws CASException
   */
  public abstract JCas getView(String localViewName) throws CASException;

  /**
   * @see org.apache.uima.cas.CAS#addFsToIndexes(FeatureStructure);
   * @param instance
   */
  public abstract void addFsToIndexes(FeatureStructure instance);

  public abstract void removeFsFromIndexes(FeatureStructure instance);

}