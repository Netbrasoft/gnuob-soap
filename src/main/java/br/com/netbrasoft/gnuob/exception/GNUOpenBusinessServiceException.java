/*
 * Copyright 2016 Netbrasoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package br.com.netbrasoft.gnuob.exception;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNU_OPEN_BUSINESS_SERVICE_EXCEPTION;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = GNU_OPEN_BUSINESS_SERVICE_EXCEPTION)
public class GNUOpenBusinessServiceException extends RuntimeException {

  private static final long serialVersionUID = 2962292431852550756L;

  public GNUOpenBusinessServiceException(final String message) {
    super(message);
  }

  public GNUOpenBusinessServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
