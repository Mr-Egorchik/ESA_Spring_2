<?xml version="1.0" encoding="UTF-8" ?>

<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:template match="ArrayList">
        <h1>
            Characters
        </h1>
        <table>
            <thead>
                <tr>
                    <td>Name</td>
                    <td>HP</td>
                    <td>Attack</td>
                    <td>Defence</td>
                    <td>Artefacts</td>
                    <td>Weapon</td>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="item">
                    <tr>
                        <td><xsl:value-of select="name"/></td>
                        <td><xsl:value-of select="hp"/></td>
                        <td><xsl:value-of select="attack"/></td>
                        <td><xsl:value-of select="defence"/></td>
                        <td>
                            <xsl:for-each select="artefacts/artefacts">
                                <xsl:value-of select="name"/>:
                                +<xsl:value-of select="bonus/bonusValue"/> <xsl:value-of select="bonus/bonusType"/>
                                <br></br>
                            </xsl:for-each>
                        </td>
                        <td>
                            <xsl:value-of select="weapon/name"/>, attack <xsl:value-of select="weapon/attack"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
</xsl:transform>