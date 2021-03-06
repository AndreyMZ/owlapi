/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.anonymous;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractAxiomsRoundTrippingTestCase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
public class AnonymousIndividualRoundtripTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Nonnull
    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAnonymousIndividual ind = AnonymousIndividual();
        OWLClass cls = Class(iri("A"));
        OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(prop, cls.getIRI(), ind);
        axioms.add(ax);
        axioms.add(Declaration(cls));
        OWLObjectProperty p = ObjectProperty(iri("p"));
        axioms.add(Declaration(p));
        OWLAnonymousIndividual anon1 = AnonymousIndividual();
        OWLAnonymousIndividual anon2 = AnonymousIndividual();
        OWLNamedIndividual ind1 = NamedIndividual(iri("j"));
        OWLNamedIndividual ind2 = NamedIndividual(iri("i"));
        axioms.add(df.getOWLObjectPropertyAssertionAxiom(p, ind1, ind2));
        axioms.add(df.getOWLObjectPropertyAssertionAxiom(p, anon1, anon1));
        axioms.add(df.getOWLObjectPropertyAssertionAxiom(p, anon2, ind2));
        axioms.add(df.getOWLObjectPropertyAssertionAxiom(p, ind2, anon2));
        return axioms;
    }

    @Override
    @Test
    public void roundTripRDFXMLAndFunctionalShouldBeSame() throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLOntology o1 = roundTrip(getOnt(), new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(getOnt(), new FunctionalSyntaxDocumentFormat());
        equal(o1, o2);
    }
}
